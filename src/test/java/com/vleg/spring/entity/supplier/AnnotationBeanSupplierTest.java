package com.vleg.spring.entity.supplier;

import com.vleg.spring.classes.entity.component.ComponentTestClassSecifiedBeanName;
import com.vleg.spring.entity.definition.BeanType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnotationBeanSupplierTest {

    private AnnotationBeanSupplier annotationBeanSupplier;

    @Before
    public void setUp() throws NoSuchMethodException {
        annotationBeanSupplier = new AnnotationBeanSupplier(ComponentTestClassSecifiedBeanName.class);
    }

    @Test
    public void getBeanName() throws Exception {
        Assert.assertEquals("componentBean", annotationBeanSupplier.getBeanName());
    }

    @Test
    public void getBeanType() throws Exception {
        Assert.assertEquals(BeanType.SINGLTON, annotationBeanSupplier.getBeanType());
    }

    @Test
    public void getCreationMethod() throws Exception {
        Assert.assertEquals(annotationBeanSupplier.getCreationMethod(), ComponentTestClassSecifiedBeanName.class.getConstructor());
    }

}