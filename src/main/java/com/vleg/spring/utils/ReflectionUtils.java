package com.vleg.spring.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ReflectionUtils {

    private static Reflections reflections = new Reflections("com.vleg.spring");

    public static Set<Class> getSuperEntities(Class aClass) {

        Set<Class> result = new HashSet<>();

        if (Objects.isNull(aClass) || Object.class.equals(aClass))
            return result;

        Set<Class> superClasses = new HashSet<>();

        Class superClass = aClass.getSuperclass();
        if (superClass != null)
            superClasses.add(superClass);

        Class[] interfaceClasses = aClass.getInterfaces();
        if (interfaceClasses != null)
            superClasses.addAll(Arrays.asList(interfaceClasses));

        for (Class forClass : superClasses) {
            result.addAll(getSuperEntities(forClass));
        }

        result.add(aClass);

        return result;
    }

    public static boolean isInstanceNearType(Object object, Class aClass) {
        Set<Class> instanceSuperTypes = getSuperEntities(object.getClass());
        Set<Class> classSuperTypes = getSuperEntities(aClass);

        return CollectionUtils.intersection(instanceSuperTypes, classSuperTypes).stream().count() > 0;
    }
}
