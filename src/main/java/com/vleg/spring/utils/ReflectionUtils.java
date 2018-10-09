package com.vleg.spring.utils;


import com.vleg.spring.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ReflectionUtils {

    public static Boolean isClassInheritsAnother(Class firstClass, Class secondClass) {
        Set<Class> firstSuperTypes = getSuperTypes(firstClass);
        return firstSuperTypes.contains(secondClass);
    }

    /**
     *
     * @return Set of classes which class inherits
     *
     * */
    public static Set<Class> getSuperTypes(Class aClass) {

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
            result.addAll(getSuperTypes(forClass));
        }

        result.add(aClass);

        return result;
    }

    public static Boolean isDefaultConstuctorPresent(Class aClass) {
        return Objects.nonNull(getDefaultConstructor(aClass));
    }

    public static Boolean isDefaultConstuctorNonPresent(Class aClass) {
        return Objects.isNull(getDefaultConstructor(aClass));
    }

    @Nullable
    public static Constructor getDefaultConstructor(Class aClass) {
        try {
            return aClass.getConstructor();
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
