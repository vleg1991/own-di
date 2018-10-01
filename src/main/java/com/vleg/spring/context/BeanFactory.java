package com.vleg.spring.context;

import com.vleg.spring.entity.BeanDefinition;
import com.vleg.spring.entity.BeanDefinitionRegistry;
import com.vleg.spring.entity.BeanResistry;
import com.vleg.spring.entity.BeanType;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanFactory {

    private final BeanDefinitionRegistry beanDefinitionRegistry = BeanDefinitionRegistry.newInstance();
    private final BeanResistry beanResistry = BeanResistry.newInstance(this);

    public Object createBeanByName(String beanName) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefenitionByName(beanName);
        return createBean(beanDefinition);
    }

    public Object createBean(BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return invokeCreation(beanDefinition, getDependencyInstances(beanDefinition.getBeanDependencies()));
    }

    private  Object invokeCreation(BeanDefinition beanDefinition, Set<Object> args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        return beanDefinition.getCreationMethod().invoke(args.toArray());
    }

    private Set<Object> getDependencyInstances(Set<Class> dependencyClasses) {
        return dependencyClasses.stream()
                .map(aClass -> beanResistry.getBeanByType(aClass))
                .collect(Collectors.toSet());
    }

    public void loadSingltonBeansToRegistry() {
        beanDefinitionRegistry.getBeanDefinitions().stream()
                .filter(beanDefinition -> BeanType.SINGLTON.equals(beanDefinition.getBeanType()))
                .sorted((o1, o2) -> {
                    if (o1.equals(o2))
                        return 0;

                    List firstBeanDependencies = Arrays.asList(o1.getBeanDependencies());
                    List secondBeanDependencies = Arrays.asList(o2.getBeanDependencies());

                    Boolean isSecondDependOn = firstBeanDependencies.stream().anyMatch(o -> secondBeanDependencies.contains(o));
                    if (isSecondDependOn)
                        return 1;
                    else
                        return -1;
                })
                .forEach(beanDefinition -> beanResistry.getBeanByName(beanDefinition.getBeanName()));
    }
}
