package com.vleg.spring.registry;

import com.vleg.spring.context.BeanDefinitionBuilder;
import com.vleg.spring.entity.definition.BeanDefinition;
import com.vleg.spring.exception.BeanNotFoundException;
import com.vleg.spring.exception.BeanResolveException;
import com.vleg.spring.utils.ReflectionUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BeanDefinitionRegistry {

    private BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.newInstance();
    private Map<String, BeanDefinition> beanDefinitionsByName = new ConcurrentHashMap<>();

    public static BeanDefinitionRegistry newInstance() {
        return new BeanDefinitionRegistry();
    }

    public BeanDefinitionRegistry() {
        beanDefinitionsByName = beanDefinitionBuilder.getBeanDefinitions().stream()
                .collect(Collectors.toMap(beanDefinition -> beanDefinition.getBeanName(), beanDefinition -> beanDefinition));
    }

    public BeanDefinition getBeanDefenitionByName(String beanName) {
        BeanDefinition result = beanDefinitionsByName.get(beanName);
        if (Objects.isNull(result))
            throw new BeanNotFoundException("Bean name: " + beanName);
        return result;
    }

    public BeanDefinition getBeanDefenitionByType(Class beanClass) {

        Set<Class> beanSuperClasses = ReflectionUtils.getSuperTypes(beanClass);

        Set<BeanDefinition> suitedBeanDefinitions = beanDefinitionsByName.values().stream()
                .filter(beanDefinition -> beanSuperClasses.contains(beanDefinition.getBeanClassType()))
                .collect(Collectors.toSet());

        if (CollectionUtils.isEmpty(suitedBeanDefinitions))
            throw new BeanNotFoundException("Class: " + beanClass);

        return resolveBean(beanClass, suitedBeanDefinitions);
    }

    public Collection<BeanDefinition> getBeanDefinitionsByName() {
        return beanDefinitionsByName.values();
    }

    private BeanDefinition resolveBean(Class beanClass, Set<BeanDefinition> beanDefinitions) {

        Set<BeanDefinition> beansWithDefaultName = beanDefinitions.stream().filter(beanDefinition -> beanDefinition.nonBeanNameSpecified()).collect(Collectors.toSet());
        Long beansWithDefaultNameCount = beansWithDefaultName.stream().count();

        if (beansWithDefaultNameCount > 1)
            throw new BeanResolveException("Too many beans defined for " + beanClass + " bean type or it super types");
        if (beansWithDefaultNameCount == 1 )
            return beansWithDefaultName.stream().findFirst().get();

        Set<BeanDefinition> beansWithSpecifiedName = beanDefinitions.stream().filter(beanDefinition -> beanDefinition.isBeanNameSpecified()).collect(Collectors.toSet());
        Long beansWithSpecifiedNameCount = beansWithSpecifiedName.stream().count();

        if (beansWithSpecifiedNameCount != 1)
            throw new BeanResolveException("Class: " + beanClass);

        return beanDefinitions.stream().findFirst().get();
    }

}
