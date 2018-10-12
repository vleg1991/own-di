package com.vleg.spring.utils;


import com.vleg.spring.annotation.BeanScan;
import com.vleg.spring.annotation.Nullable;
import com.vleg.spring.exception.ApplicationInitiationException;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

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

    /**
     *
     * @return reference to specified for scaning package or <br/>default reference to package contains main class
     *
     * */
    public static Reflections getApplicationBeanScanReflections() {
        try {
            Class mainClass = Class.forName(System.getProperty("sun.java.command"));
            Class returnType = mainClass.getMethod("main", String[].class).getReturnType();
            if ("void".toUpperCase().equals(returnType.getName().toUpperCase())) {
                String searchPath = ((BeanScan) mainClass.getAnnotation(BeanScan.class)).path();
                if (!"".equals(searchPath))
                    return new Reflections(searchPath);
            }
        } catch (Exception e) {
            return getDefaultApplicationReflections();
        }
        return null;
    }

    public static Reflections getDefaultApplicationReflections() {
        try {
            ClassLoader[] classLoaders = new ClassLoader[]{ClassLoader.getSystemClassLoader()};
            ConfigurationBuilder configuration = ConfigurationBuilder.build();
            configuration.setClassLoaders(classLoaders);
            configuration.setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner());
            Reflections reflections = new Reflections(
                    configuration
            );
            return reflections;
        }  catch (Exception e) {
            throw new ApplicationInitiationException("Method \"main\" does not void return type, or check ComponentScan annotation for specifying component and configuration class for search");
        }
    }

}
