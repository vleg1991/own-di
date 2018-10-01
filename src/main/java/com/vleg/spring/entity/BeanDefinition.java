package com.vleg.spring.entity;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanDefinition {

    private final String beanName;
    private final BeanType beanType;
    private final CreationMethod creationMethod;
    private final Class configurationClass;
    private final Set<Class> beanDependencies;

    public BeanDefinition(String beanName, BeanType beanType, Executable creationMethod) {
        this.beanName = beanName;
        this.beanType = beanType;
        if (Method.class.equals(creationMethod.getClass()))
            this.creationMethod = new CreationMethod(creationMethod, CreationType.METHOD);
        else
            this.creationMethod = new CreationMethod(creationMethod, CreationType.CONSTRUCTOR);
        this.configurationClass = creationMethod.getDeclaringClass();
        this.beanDependencies = Arrays.asList(creationMethod.getParameterTypes()).stream().collect(Collectors.toSet());
    }

    public String getBeanName() {
        return beanName;
    }

    public BeanType getBeanType() {
        return beanType;
    }

    public CreationMethod getCreationMethod() {
        return creationMethod;
    }

    public Class getConfigurationClass() {
        return configurationClass;
    }

    public Set<Class> getBeanDependencies() {
        return beanDependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o.getClass().equals(BeanDefinition.class))) return false;
        BeanDefinition that = (BeanDefinition) o;
        return beanType == that.beanType &&
                Objects.equals(beanName, that.beanName) &&
                Objects.equals(creationMethod, that.creationMethod) &&
                Objects.equals(configurationClass, that.configurationClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName, beanType, creationMethod, configurationClass, beanDependencies);
    }
}
