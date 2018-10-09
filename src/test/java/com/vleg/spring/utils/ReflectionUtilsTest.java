package com.vleg.spring.utils;

import com.vleg.spring.classes.AnnotatedTestClassWithConstructor;
import com.vleg.spring.classes.AnnotatedTestClassWithoutConstructor;
import com.vleg.spring.classes.NonAnnotatedTestClass;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

public class ReflectionUtilsTest {

    @Test
    public void checkGettingSuperClasses() throws Exception {
        Assert.assertTrue(CollectionUtils.isEmpty(ReflectionUtils.getSuperTypes(Object.class)));
        Assert.assertTrue(CollectionUtils.isEmpty(ReflectionUtils.getSuperTypes(null)));
        Assert.assertTrue(ReflectionUtils.getSuperTypes(AnnotatedTestClassWithoutConstructor.class).stream().count() == 2);
        Assert.assertTrue(ReflectionUtils.getSuperTypes(AnnotatedTestClassWithoutConstructor.class).contains(NonAnnotatedTestClass.class));
    }

    @Test
    public void checkInheritance() throws Exception {
        Assert.assertTrue(ReflectionUtils.isClassInheritsAnother(AnnotatedTestClassWithoutConstructor.class, NonAnnotatedTestClass.class));
        Assert.assertFalse(ReflectionUtils.isClassInheritsAnother(NonAnnotatedTestClass.class, AnnotatedTestClassWithoutConstructor.class));
    }

    @Test
    public void checkIsDefaultConstuctorPresent() {
        Assert.assertTrue(ReflectionUtils.isDefaultConstuctorPresent(AnnotatedTestClassWithoutConstructor.class));
    }

    @Test
    public void checkIsDefaultConstuctorNonPresent() {
        Assert.assertTrue(ReflectionUtils.isDefaultConstuctorNonPresent(AnnotatedTestClassWithConstructor.class));
    }

    @Test
    public void checkGetDefaultConstructor() {
        Assert.assertNotNull(ReflectionUtils.getDefaultConstructor(AnnotatedTestClassWithoutConstructor.class));
        Assert.assertNull(ReflectionUtils.getDefaultConstructor(AnnotatedTestClassWithConstructor.class));
    }
}