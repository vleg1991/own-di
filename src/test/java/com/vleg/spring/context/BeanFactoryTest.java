package com.vleg.spring.context;

import com.vleg.spring.classes.entity.component.ComponentTestClass;
import com.vleg.spring.entity.definition.BeanDefinition;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {

    private BeanFactory beanFactory = new BeanFactory();

    @Test
    public void getBeanDefinitionRegistry() throws Exception {
        assertNotNull(beanFactory.getBeanDefinitionRegistry());
    }

    @Test
    public void getBeanResistry() throws Exception {
        assertNotNull(beanFactory.getBeanResistry());
    }

    @Test
    public void createBeanByType() throws Exception {
        Object beanByType = beanFactory.createBeanByType(ComponentTestClass.class);
        Assert.assertNotNull(beanByType);
        Assert.assertEquals(ComponentTestClass.class, beanByType.getClass());
    }

    @Test
    public void createBeanByName() throws Exception {
        Object beanByName = beanFactory.createBeanByName("namedStringBean");
        Assert.assertNotNull(beanByName);
        Assert.assertEquals(String.class, beanByName.getClass());
        Assert.assertNotNull(beanFactory.getBeanResistry().getBeanByName("namedStringBean"));
    }

    @Test
    public void createBean() throws Exception {

        BeanDefinition beanDefinition = beanFactory.getBeanDefinitionRegistry().getBeanDefenitionByType(ComponentTestClass.class);
        Assert.assertNotNull(beanFactory.createBean(beanDefinition));

        String beanName = beanFactory.getBeanDefinitionRegistry().getBeanDefenitionByType(ComponentTestClass.class).getBeanName();
        Assert.assertNotNull(beanFactory.getBeanResistry().getBeanByName(beanName));
    }

    @Test
    public void loadSingltonBeansToRegistry() throws Exception {
        beanFactory.loadSingltonBeansToRegistry();
    }

}