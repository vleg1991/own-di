package com.vleg.spring.entity.definition;

import java.util.Objects;

public class BeanDependency {

    private final Class beanType;
    private final String beanReferenceName;

    public BeanDependency(Class beanType, String beanReferenceName) {
        this.beanType = beanType;
        this.beanReferenceName = beanReferenceName;
    }

    public Class getBeanType() {
        return beanType;
    }

    public String getBeanReferenceName() {
        return beanReferenceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeanDependency)) return false;
        BeanDependency that = (BeanDependency) o;
        return Objects.equals(beanType, that.beanType) &&
                Objects.equals(beanReferenceName, that.beanReferenceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanType, beanReferenceName);
    }

    @Override
    public String toString() {
        return "BeanDependency{" +
                "beanType=" + beanType +
                ", beanReferenceName='" + beanReferenceName + '\'' +
                '}';
    }
}
