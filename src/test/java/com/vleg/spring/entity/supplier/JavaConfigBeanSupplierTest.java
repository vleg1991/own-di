package com.vleg.spring.entity.supplier;

import com.vleg.spring.classes.entity.config.ConfigurationTestClass;
import com.vleg.spring.entity.definition.BeanType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

public class JavaConfigBeanSupplierTest {

    private JavaConfigBeanSupplier javaConfigBeanSupplier;

    @Before
    public void setUp() throws NoSuchMethodException {
        Method configurationMethod = ConfigurationTestClass.class.getMethod("getNamedStringBean");
        javaConfigBeanSupplier = new JavaConfigBeanSupplier(configurationMethod);
    }

    @Test
    public void getBeanName() throws Exception {
        Assert.assertEquals("namedStringBean", javaConfigBeanSupplier.getBeanName());
    }

    @Test
    public void getBeanType() throws Exception {
        Assert.assertEquals(BeanType.SINGLTON, javaConfigBeanSupplier.getBeanType());
    }

    @Test
    public void getCreationMethod() throws Exception {
        Assert.assertEquals(javaConfigBeanSupplier.getCreationMethod(), ConfigurationTestClass.class.getMethod("getNamedStringBean"));
    }

}