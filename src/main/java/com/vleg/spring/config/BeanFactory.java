package com.vleg.spring.config;

import com.vleg.spring.entity.BeanDefinition;
import com.vleg.spring.entity.BeanDefinitionRegistry;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BeanFactory {

    private final BeanDefinitionRegistry beanDefinitionRegistry = BeanDefinitionRegistry.newInstance();

    public Object getBean(String beanName) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefenitionByName(beanName);
        return createBean(beanDefinition);
    }

    public Object createBean(BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return invokeCreation(beanDefinition);
    }

//    public List<Object> createBeans(List<BeanDefinition> beanDefinitions) throws IllegalAccessException, InstantiationException, InvocationTargetException {
//        List<Object> beans = new ArrayList<>();
//        for (BeanDefinition beanDefinition : beanDefinitions) {
//            beans.add(invokeCreation(beanDefinition));
//        }
//        return beans;
//    }

    private  Object invokeCreation(BeanDefinition beanDefinition) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Object configInstance = beanDefinition.getConfigurationClass().newInstance();
        List<Object> paramInstances = getClassInstances(beanDefinition.getBeanDependencies());
        return beanDefinition.getCreationMethod().invoke(configInstance, paramInstances);
    }

    private List<Object> getClassInstances(List<Class> paramsClasses) throws InstantiationException, IllegalAccessException {
        List<Object> result = new ArrayList<>();
        for (Class aClass : paramsClasses)
            result.add(aClass.newInstance());
        return result;
    }
}
