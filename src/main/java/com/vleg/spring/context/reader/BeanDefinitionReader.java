package com.vleg.spring.context.reader;

import com.vleg.spring.entity.supplier.BeanSupplier;

import java.util.Set;

public interface BeanDefinitionReader {

    Set<BeanSupplier> getSuppliers();
    BeanSupplier getSupplierByBeanName(String beanName);
}
