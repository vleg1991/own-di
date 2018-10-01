package com.vleg.spring.entity;

import com.vleg.spring.annotation.Bean;
import com.vleg.spring.annotation.Configuration;
import com.vleg.spring.annotation.Scope;
import org.h2.util.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BeanDefinitionRegistry {

    private final Set<BeanDefinition> beanDefinitions = searchBeanDefinitions();

    public static BeanDefinitionRegistry newInstance() {
        return new BeanDefinitionRegistry();
    }

    public BeanDefinition getBeanDefenitionByName(String beanName) {
        return beanDefinitions.stream()
                .filter(beanDefinition -> beanName != null && beanName.equals(beanDefinition.getBeanName())).findFirst().get();
    }

    public Set<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    private Set<BeanDefinition> searchBeanDefinitions() {

        Set<BeanDefinition> result = new HashSet<>();

        // TODO Проверить как будет работать если взять конструктор без параметров
        Reflections reflections = new Reflections("com.vleg.spring");

        Set<Class<?>> configurationClasses = reflections.getTypesAnnotatedWith(Configuration.class);

        configurationClasses.stream().forEach(aClass ->
                Arrays.asList(aClass.getMethods()).stream()
                        .filter(method -> method.isAnnotationPresent(Bean.class))
                        .forEach(method ->
                                result.add(new BeanDefinition(resolveBeanName(method), resolveBeanType(method), method))
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

    private BeanType resolveBeanType(Method method){
        Scope scopeAnnotation = method.getAnnotation(Scope.class);
        if (Objects.nonNull(scopeAnnotation)) {
            return scopeAnnotation.name();
        }
        return BeanType.SINGLTON;
    }
}
