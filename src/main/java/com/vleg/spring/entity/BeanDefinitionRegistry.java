package com.vleg.spring.entity;

import com.vleg.spring.annotation.Bean;
import com.vleg.spring.annotation.Configuration;
import org.h2.util.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitions = searchBeanDefinitions();

    public static BeanDefinitionRegistry newInstance() {
        return new BeanDefinitionRegistry();
    }

    public BeanDefinition getBeanDefenitionByName(String beanName) {
        return beanDefinitions.get(beanName);
    }

    public Map<String, BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    private Map<String, BeanDefinition> searchBeanDefinitions() {

        Map<String, BeanDefinition> result = new ConcurrentHashMap<>();

        // TODO Проверить как будет работать если взять конструктор без параметров
        Reflections reflections = new Reflections("com.vleg.spring");

        Set<Class<?>> configurationClasses = reflections.getTypesAnnotatedWith(Configuration.class);

        configurationClasses.stream().forEach(aClass ->
                Arrays.asList(aClass.getMethods()).stream()
                        .filter(method -> method.isAnnotationPresent(Bean.class))
                        .forEach(method ->
                                result.put(
                                        resolveBeanName(method),
                                        new BeanDefinition(method)
                                )
                        )
        );

        return result;
    }

    private String resolveBeanName(Method method) {
        String specifiedBeanName = method.getAnnotation(Bean.class).name();
        return StringUtils.isNullOrEmpty(specifiedBeanName)
                ? method.getReturnType().getName()
                : specifiedBeanName;
    }
}
