package com.vleg.spring.context;

import com.vleg.spring.entity.definition.BeanDefinition;
import com.vleg.spring.entity.definition.BeanType;
import com.vleg.spring.exception.BeanCreationException;
import com.vleg.spring.registry.BeanDefinitionRegistry;
import com.vleg.spring.registry.BeanResistry;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BeanFactory {

    private final BeanDefinitionRegistry beanDefinitionRegistry = BeanDefinitionRegistry.newInstance();
    private final BeanResistry beanResistry = BeanResistry.newInstance(this);
    private Map<String, BeanDefinition> beanInCreationMap = new ConcurrentHashMap<>();

    public BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return beanDefinitionRegistry;
    }

    public BeanResistry getBeanResistry() {
        return beanResistry;
    }

    public Object createBeanByType(Class beanClass) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefenitionByType(beanClass);
        return createBean(beanDefinition);
    }

    public Object createBeanByName(String beanName) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefenitionByName(beanName);
        return createBean(beanDefinition);
    }

    public Object createBean(BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException, InvocationTargetException {

        beanInCreationMap.put(beanDefinition.getBeanName(), beanDefinition);

        Set<BeanDefinition> depencencyBeanDefinitions = beanDefinition.getBeanDependencies().stream()
                .map(beanDependency -> {
                    if (Objects.nonNull(beanDependency.getBeanReferenceName())
                            && !"".equals(beanDependency.getBeanReferenceName()))
                        return beanDefinitionRegistry.getBeanDefenitionByName(beanDependency.getBeanReferenceName());
                    else
                        return beanDefinitionRegistry.getBeanDefenitionByType(beanDependency.getBeanType());
                })
                .collect(Collectors.toSet());

        Object beanInstance;
        if (CollectionUtils.isEmpty(depencencyBeanDefinitions))
            beanInstance = invokeCreation(beanDefinition);
        else
            beanInstance = invokeCreation(beanDefinition, getDependencyInstances(depencencyBeanDefinitions));

        beanInCreationMap.remove(beanDefinition.getBeanName());

        return beanInstance;
    }

    private Set<Object> getDependencyInstances(Set<BeanDefinition> dependencyClasses) {

        if (isAnyDependencyBeanInCreation(dependencyClasses))
            throw new BeanCreationException("Is there circular dependencies?");

        return dependencyClasses.stream()
                .map(beanDefinition -> beanResistry.getBeanByName(beanDefinition.getBeanName()))
                .collect(Collectors.toSet());
    }

    private boolean isAnyDependencyBeanInCreation(Set<BeanDefinition> dependencyClasses) {
        return dependencyClasses.stream()
                .map(beanDefinition -> beanDefinition.getBeanName())
                .anyMatch(beanName -> Objects.nonNull(beanInCreationMap.get(beanName)));
    }

    private  Object invokeCreation(BeanDefinition beanDefinition, Set<Object> args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        return beanDefinition.getCreationMethod().invoke(args.toArray());
    }

    private  Object invokeCreation(BeanDefinition beanDefinition) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        return beanDefinition.getCreationMethod().invoke(new Objects[]{});
    }

    public void loadSingltonBeansToRegistry() {
        beanDefinitionRegistry.getBeanDefinitionsByName().stream()
                .filter(beanDefinition -> BeanType.SINGLTON.equals(beanDefinition.getBeanType()))
                .forEach(beanDefinition -> beanResistry.getBeanByName(beanDefinition.getBeanName()));
    }
}
