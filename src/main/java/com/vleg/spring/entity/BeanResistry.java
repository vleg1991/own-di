package com.vleg.spring.entity;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vleg.spring.context.BeanFactory;
import com.vleg.spring.exception.BeanNotFoundException;
import com.vleg.spring.exception.BeanResolveException;
import com.vleg.spring.utils.ReflectionUtils;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class BeanResistry {

    private LoadingCache<String, Object> beans;

    public static BeanResistry newInstance(BeanFactory beanFactory) {
        BeanResistry beanResistry = new BeanResistry();
        beanResistry.beans = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String beanName) throws Exception {
                        return beanFactory.createBeanByName(beanName);
                    }
                });
        return beanResistry;
    }

    public Object getBeanByName(String beanName)  {
        try {
            return beans.get(beanName);
        } catch (ExecutionException e) {
            throw new BeanNotFoundException("Cannot load bean into cache.");
        }
    }

    public Object getBeanByType(Class beanType) {

        Set<Object> suitableInstances = beans.asMap().values().stream()
                .filter(o -> ReflectionUtils.isInstanceNearType(o, beanType))
                .collect(Collectors.toSet());

        if (suitableInstances.stream().count() != 1)
            throw new BeanResolveException("Cannot resolve instance by type");

        return suitableInstances.stream().findFirst().get();
    }

    public Object getBeanRefreshedBean(String beanName)  {
        beans.refresh(beanName);
        try {
            return beans.get(beanName);
        } catch (ExecutionException e) {
            throw new BeanNotFoundException("Cannot load bean into cache.");
        }
    }
}
