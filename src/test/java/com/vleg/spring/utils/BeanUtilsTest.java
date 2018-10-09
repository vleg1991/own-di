package com.vleg.spring.utils;

import com.vleg.spring.annotation.Scope;
import com.vleg.spring.classes.entity.component.ComponentTestClass;
import com.vleg.spring.classes.entity.component.ComponentTestClassSecifiedBeanName;
import com.vleg.spring.entity.definition.BeanType;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;

public class BeanUtilsTest {

    @Test
    public void resolveBeanType() throws Exception {
        Scope scope = ComponentTestClass.class.getAnnotation(Scope.class);
        Assert.assertEquals(BeanType.PROTOTYPE, scope.name());
    }

    @Test
    public void getAutowiredConstructor() throws Exception {
        Constructor constructor1 = ComponentTestClass.class.getConstructor(String.class);
        Assert.assertEquals(constructor1, BeanUtils.getAutowiredConstructor(ComponentTestClass.class));

        Constructor constructor2 = ComponentTestClassSecifiedBeanName.class.getConstructor();
        Assert.assertEquals(constructor2, BeanUtils.getAutowiredConstructor(ComponentTestClassSecifiedBeanName.class));
    }

}