package com.vleg.spring.entity;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vleg.spring.config.BeanFactory;
import com.vleg.spring.exception.BeanNotFoundException;

import java.util.concurrent.ExecutionException;

public class BeanResistry {

    private LoadingCache<String, Object> beans;
    private BeanFactory beanFactory;

    public static BeanResistry newInstance() {
        BeanResistry beanResistry = new BeanResistry();
        beanResistry.beanFactory = new BeanFactory();
        beanResistry.beans = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String beanName) throws Exception {
                        return beanResistry.getBeanFactory().getBean(beanName);
                    }
                });
        return beanResistry;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public Object getBeanByName(String beanName)  {
        try {
            return beans.get(beanName);
        } catch (ExecutionException e) {
            throw new BeanNotFoundException("Cannot load bean into cache.");
        }
    }

    public Object getBeanNewInstance(String beanName)  {
        beans.refresh(beanName);
        try {
            return beans.get(beanName);
        } catch (ExecutionException e) {
            throw new BeanNotFoundException("Cannot load bean into cache.");
        }
    }
}
