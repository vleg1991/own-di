package com.vleg.spring.utils;

import com.vleg.spring.annotation.Autowired;
import com.vleg.spring.annotation.Scope;
import com.vleg.spring.entity.definition.BeanType;
import com.vleg.spring.exception.BeanResolveException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanUtils {

    public static BeanType resolveBeanType(Annotation annotation){
        Scope scopeAnnotation = (Scope) annotation;
        if (Objects.nonNull(scopeAnnotation)) {
            return scopeAnnotation.name();
        }
        return BeanType.SINGLTON;
    }

    /**
     *  @param aClass - component class
     *  @throws BeanResolveException
     *  @return constructor of the aClass which marked as @Autowired or default constructor
     * */
    public static Constructor getAutowiredConstructor(Class aClass) {

        Set<Constructor> annotatedConstructors = Arrays.stream(aClass.getConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(Autowired.class))
                .collect(Collectors.toSet());

        Long countOfAnnotatedConstuctors = annotatedConstructors.stream().count();
        if (countOfAnnotatedConstuctors > 1
                || (countOfAnnotatedConstuctors == 0 && ReflectionUtils.isDefaultConstuctorNonPresent(aClass))) {
            throw new BeanResolveException("Component should contains constructor annotated by @Autowired OR public default (no params) constructor.\n" +
                    " Class: " + aClass);
        }

        return countOfAnnotatedConstuctors == 1
                ? annotatedConstructors.stream().findFirst().get()
                : ReflectionUtils.getDefaultConstructor(aClass);
    }
}
