package com.vleg.spring.entity.supplier;

import com.vleg.spring.annotation.Component;
import com.vleg.spring.annotation.Scope;
import com.vleg.spring.entity.definition.BeanType;
import com.vleg.spring.utils.BeanUtils;

import java.lang.reflect.Executable;
import java.util.Objects;
import java.util.UUID;

public class AnnotationBeanSupplier implements BeanSupplier {

    private Class beanSupplier;

    public AnnotationBeanSupplier(Class beanSupplier) {
        this.beanSupplier = beanSupplier;
    }

    @Override
    public String getBeanName() {
        String specifiedName = ((Component)beanSupplier.getAnnotation(Component.class)).name();
        String defaultName = UUID.randomUUID().toString();
        return specifiedName != null && !"".equals(specifiedName)
                ? specifiedName
                : defaultName;
    }

    @Override
    public BeanType getBeanType() {
        return BeanUtils.resolveBeanType(beanSupplier.getAnnotation(Scope.class));
    }

    @Override
    public Executable getCreationMethod() {
        return BeanUtils.getAutowiredConstructor(beanSupplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnnotationBeanSupplier)) return false;
        AnnotationBeanSupplier that = (AnnotationBeanSupplier) o;
        return Objects.equals(beanSupplier, that.beanSupplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanSupplier);
    }
}
