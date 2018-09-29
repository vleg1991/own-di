package com.vleg.spring.entity;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class BeanDefinition {

    private final BeanType beanType;
    private final Method creationMethod;
    private final Class configurationClass;
    private final List<Class> beanDependencies;

    public BeanDefinition(Method creationMethod, BeanType beanType) {
        this.beanType = beanType;
        this.creationMethod = creationMethod;
        this.configurationClass = creationMethod.getDeclaringClass();
        this.beanDependencies = Arrays.asList(creationMethod.getParameterTypes());
    }

    public BeanType getBeanType() {
        return beanType;
    }

    public Method getCreationMethod() {
        return creationMethod;
    }

    public Class getConfigurationClass() {
        return configurationClass;
    }

    public List<Class> getBeanDependencies() {
        return beanDependencies;
    }
}
