package com.vleg.spring.entity.supplier;

import com.vleg.spring.entity.definition.BeanType;

import java.lang.reflect.Executable;

public interface BeanSupplier {

    String getBeanName();
    BeanType getBeanType();
    Executable getCreationMethod();
}
