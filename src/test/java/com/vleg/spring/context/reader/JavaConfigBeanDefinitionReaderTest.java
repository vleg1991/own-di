package com.vleg.spring.context.reader;

import com.vleg.spring.classes.entity.config.ConfigurationTestClass;
import com.vleg.spring.entity.definition.BeanType;
import com.vleg.spring.entity.supplier.BeanSupplier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JavaConfigBeanDefinitionReaderTest {

    private JavaConfigBeanDefinitionReader javaConfigBeanDefinitionReader;

    @Before
    public void setUp() throws Exception {
        javaConfigBeanDefinitionReader = JavaConfigBeanDefinitionReader.newInstance();
    }

    @Test
    public void getSuppliers() throws Exception {
        Assert.assertTrue(javaConfigBeanDefinitionReader.getSuppliers().stream().count() == 4);
    }

    @Test
    public void getSupplierByBeanName() throws Exception {
        Assert.assertEquals(null, javaConfigBeanDefinitionReader.getSupplierByBeanName(""));
        Assert.assertEquals(null, javaConfigBeanDefinitionReader.getSupplierByBeanName(null));
        BeanSupplier beanSupplier = javaConfigBeanDefinitionReader.getSupplierByBeanName("namedStringBean");
        Assert.assertEquals("namedStringBean", beanSupplier.getBeanName());
        Assert.assertEquals(BeanType.SINGLTON, beanSupplier.getBeanType());
        Assert.assertEquals(ConfigurationTestClass.class.getMethod("getNamedStringBean"), beanSupplier.getCreationMethod());
    }

}