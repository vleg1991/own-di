package com.vleg.spring.entity.supplier;

import com.vleg.spring.annotation.Bean;
import com.vleg.spring.annotation.Scope;
import com.vleg.spring.entity.definition.BeanType;
import com.vleg.spring.utils.BeanUtils;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

public class JavaConfigBeanSupplier implements BeanSupplier {

    private Method beanSupplier;

    public JavaConfigBeanSupplier(Method beanSupplier) {
        this.beanSupplier = beanSupplier;
    }

    @Override
    public String getBeanName() {
        String specifiedName = beanSupplier.getAnnotation(Bean.class).name();
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
        return beanSupplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JavaConfigBeanSupplier)) return false;
        JavaConfigBeanSupplier that = (JavaConfigBeanSupplier) o;
        return Objects.equals(beanSupplier, that.beanSupplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanSupplier);
    }
}
