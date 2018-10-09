package com.vleg.spring.registry;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vleg.spring.context.BeanFactory;
import com.vleg.spring.exception.BeanNotFoundException;

import java.util.concurrent.ExecutionException;

public class BeanResistry {

    private LoadingCache<String, Object> beansByNames;

    public static BeanResistry newInstance(BeanFactory beanFactory) {
        BeanResistry beanResistry = new BeanResistry();
        beanResistry.beansByNames = CacheBuilder.newBuilder()
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
            return beansByNames.get(beanName);
        } catch (ExecutionException e) {
            throw new BeanNotFoundException("Cannot load bean into cache.");
        }
    }

    public Object getBeanRefreshedBean(String beanName)  {
        beansByNames.refresh(beanName);
        try {
            return beansByNames.get(beanName);
        } catch (ExecutionException e) {
            throw new BeanNotFoundException("Cannot load bean into cache.");
        }
    }
}
