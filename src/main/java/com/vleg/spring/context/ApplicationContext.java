package com.vleg.spring.context;

import com.vleg.spring.entity.BeanResistry;

public class ApplicationContext {

    private final BeanResistry beanResistry;

    public ApplicationContext() {
        this.beanResistry = BeanResistry.newInstance();
    }


}
