package com.vleg.spring.registry;

import com.vleg.spring.classes.entity.component.ComponentTestClass;
import com.vleg.spring.context.BeanFactory;
import org.junit.Assert;
import org.junit.Test;

public class BeanResistryTest {

    private BeanFactory beanFactory = new BeanFactory();
    private BeanResistry beanResistry = beanFactory.getBeanResistry();

    @Test
    public void getBeanByName() throws Exception {
        Object bean = beanResistry.getBeanByName("namedStringBean");
        Assert.assertEquals("Named String Bean", bean);
    }

    @Test
    public void getBeanRefreshedBean() throws Exception {
        String beanName = beanFactory.getBeanDefinitionRegistry().getBeanDefenitionByType(ComponentTestClass.class).getBeanName();
        Object bean = beanResistry.getBeanRefreshedBean(beanName);
        Assert.assertEquals("Test string", ((ComponentTestClass) bean).getValue());
    }

}