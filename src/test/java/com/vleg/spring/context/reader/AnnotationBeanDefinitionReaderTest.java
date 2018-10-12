package com.vleg.spring.context.reader;

import com.vleg.spring.classes.entity.component.ComponentTestClassSecifiedBeanName;
import com.vleg.spring.entity.definition.BeanType;
import com.vleg.spring.entity.supplier.BeanSupplier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnotationBeanDefinitionReaderTest {

    private AnnotationBeanDefinitionReader annotationBeanDefinitionReader;

    @Before
    public void setUp() throws Exception {
        System.out.println(System.getProperty("sun.java.command"));
        annotationBeanDefinitionReader = AnnotationBeanDefinitionReader.newInstance();
        System.out.println("1");
    }

    @Test
    public void getSuppliers() throws Exception {
        Assert.assertTrue(annotationBeanDefinitionReader.getSuppliers().stream().count() == 4);
    }

    @Test
    public void getSupplierByBeanName() throws Exception {
        Assert.assertEquals(null, annotationBeanDefinitionReader.getSupplierByBeanName(""));
        Assert.assertEquals(null, annotationBeanDefinitionReader.getSupplierByBeanName(null));
        BeanSupplier beanSupplier = annotationBeanDefinitionReader.getSupplierByBeanName("componentBean");
        Assert.assertEquals("componentBean", beanSupplier.getBeanName());
        Assert.assertEquals(BeanType.SINGLTON, beanSupplier.getBeanType());
        Assert.assertEquals(ComponentTestClassSecifiedBeanName.class.getConstructor(), beanSupplier.getCreationMethod());
    }

}