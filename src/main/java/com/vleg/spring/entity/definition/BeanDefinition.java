package com.vleg.spring.entity.definition;

import com.vleg.spring.annotation.Qualifier;
import com.vleg.spring.exception.BeanResolveException;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BeanDefinition {

    private final String beanName;
    private final BeanType beanType;
    private final CreationMethod creationMethod;
    private final Class configurationClass;
    private final Set<BeanDependency> beanDependencies;

    public BeanDefinition(String beanName, BeanType beanType, Executable creationMethod) {
        this.beanName = beanName;
        this.beanType = beanType;
        if (Method.class.equals(creationMethod.getClass()))
            this.creationMethod = new CreationMethod(creationMethod, CreationType.METHOD);
        else
            this.creationMethod = new CreationMethod(creationMethod, CreationType.CONSTRUCTOR);
        this.configurationClass = creationMethod.getDeclaringClass();
        this.beanDependencies = Arrays.stream(creationMethod.getParameters())
                .map(parameter -> {
                    Qualifier annotation = parameter.getAnnotation(Qualifier.class);
                    if (Objects.nonNull(annotation))
                        return new BeanDependency(parameter.getType(), annotation.beanName());
                    else
                        return new BeanDependency(parameter.getType(), "");
                })
                .collect(Collectors.toSet());
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

    public Set<BeanDependency> getBeanDependencies() {
        return beanDependencies;
    }

    public Class getBeanClassType() {
        if (CreationType.CONSTRUCTOR.equals(creationMethod.getCreationType()))
            return configurationClass;
        if (CreationType.METHOD.equals(creationMethod.getCreationType()))
            return ((Method)creationMethod.getCreationMethod()).getReturnType();
        throw new BeanResolveException("Cannot resolve bean class type");
    }

    public Boolean nonBeanNameSpecified() {
        return !isBeanNameSpecified();
    }

    public Boolean isBeanNameSpecified() {
        try {
            UUID.fromString(beanName);
        } catch (Exception e) {
            return true;
        }
        return false;
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

    @Override
    public String toString() {
        return "Bean class type=" + getBeanClassType() +
               ", configurationClass=" + configurationClass +
               ", beanDependencies=" + beanDependencies +
               "}\n";
    }
}
