package com.vleg.spring.entity;

import com.vleg.spring.annotation.*;
import com.vleg.spring.exception.BeanResolveException;
import org.h2.util.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
        result.addAll(getConfigurationBeans());
        result.addAll(getComponents());
        return result;
    }

    public Set<BeanDefinition> getConfigurationBeans () {

        Set<BeanDefinition> result = new HashSet<>();

        // TODO Проверить как будет работать если взять конструктор без параметров
        Reflections reflections = new Reflections("com.vleg.spring");

        Set<Class<?>> configurationClasses = reflections.getTypesAnnotatedWith(Configuration.class);

        configurationClasses.stream().forEach(aClass ->
                Arrays.asList(aClass.getMethods()).stream()
                        .filter(method -> method.isAnnotationPresent(Bean.class))
                        .forEach(method ->
                                result.add(new BeanDefinition(resolveBeanName(method), resolveBeanType(method.getAnnotation(Scope.class)), method))
                        )
        );

        return result;
    }

    public Set<BeanDefinition> getComponents() {

        Set<BeanDefinition> result = new HashSet<>();

        Reflections reflections = new Reflections("com.vleg.spring");

        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);

        componentClasses.stream().forEach(aClass -> {

            String beanName = aClass.getName();
            BeanType beanType = resolveBeanType(aClass.getAnnotation(Scope.class));

            Set<Constructor> annotatedConstructors = Arrays.stream(aClass.getDeclaredConstructors())
                    .filter(constructor -> constructor.isAnnotationPresent(Autowired.class))
                    .collect(Collectors.toSet());
            if (annotatedConstructors.stream().count() != 1)
                throw new BeanResolveException();
            Constructor beanConstructor = annotatedConstructors.stream().findFirst().get();

            result.add(new BeanDefinition(beanName, beanType, beanConstructor));
        });

        return result;
    }

    private String resolveBeanName(Method method) {
        String specifiedBeanName = method.getAnnotation(Bean.class).name();
        return StringUtils.isNullOrEmpty(specifiedBeanName)
                ? method.getReturnType().getName()
                : specifiedBeanName;
    }

    private BeanType resolveBeanType(Scope scopeAnnotation){
        if (Objects.nonNull(scopeAnnotation)) {
            return scopeAnnotation.name();
        }
        return BeanType.SINGLTON;
    }
}
