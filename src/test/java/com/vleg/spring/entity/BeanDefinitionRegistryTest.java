package com.vleg.spring.entity;

import com.vleg.spring.classes.entity.component.ComponentTestClass;
import com.vleg.spring.classes.entity.component.ComponentTestClassSecifiedBeanName;
import com.vleg.spring.classes.entity.config.ConfigurationTestClass;
import com.vleg.spring.entity.definition.BeanDefinition;
import com.vleg.spring.entity.definition.BeanType;
import com.vleg.spring.exception.BeanNotFoundException;
import com.vleg.spring.registry.BeanDefinitionRegistry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeanDefinitionRegistryTest {

    BeanDefinitionRegistry beanDefinitionRegistry;

    @Before
    public void setUp() {
        beanDefinitionRegistry = BeanDefinitionRegistry.newInstance();
    }

    /**
     * @see com.vleg.spring.classes.entity
     * */
    @Test
    public void testReadBeanWithSpecifiedName() {
        Long cntOfNamedStringBeans = beanDefinitionRegistry.getBeanDefinitions().stream()
                .filter(beanDefinition -> String.class.equals(beanDefinition.getBeanClassType())
                        && ConfigurationTestClass.class.equals(beanDefinition.getConfigurationClass()))
                .count();
        Assert.assertTrue(cntOfNamedStringBeans == 2);
        Long cntOfComponentBeans = beanDefinitionRegistry.getBeanDefinitions().stream()
                .filter(beanDefinition -> ComponentTestClassSecifiedBeanName.class.equals(beanDefinition.getBeanClassType()))
                .count();
        Assert.assertTrue(cntOfComponentBeans == 1);
    }

    @Test
    public void testReadBeanWithSpecifiedScope() {
        Long cntOfPrototypeBeans = beanDefinitionRegistry.getBeanDefinitions().stream()
                .filter(beanDefinition -> BeanType.PROTOTYPE.equals(beanDefinition.getBeanType())
                        && ComponentTestClass.class.equals(beanDefinition.getConfigurationClass()))
                .count();
        Assert.assertTrue(cntOfPrototypeBeans == 1);
    }

    @Test
    public void testBeanDefinitionContent() throws Exception {
        BeanDefinition configBeanDefinition = beanDefinitionRegistry.getBeanDefenitionByName("namedStringBean");
        BeanDefinition componentBeanDefinition = beanDefinitionRegistry.getBeanDefenitionByType(ComponentTestClass.class);
        // BeanType content
        Assert.assertTrue(BeanType.SINGLTON.equals(configBeanDefinition.getBeanType()));
        Assert.assertTrue(BeanType.PROTOTYPE.equals(componentBeanDefinition.getBeanType()));
        // Configuration class content
        Assert.assertTrue(ConfigurationTestClass.class.equals(configBeanDefinition.getConfigurationClass()));
        Assert.assertTrue(ComponentTestClass.class.equals(componentBeanDefinition.getConfigurationClass()));
        // Creation method content
        Assert.assertTrue(ConfigurationTestClass.class.getMethod("getNamedStringBean").equals(configBeanDefinition.getCreationMethod().getCreationMethod()));
        Assert.assertTrue(ComponentTestClass.class.getConstructor(String.class).equals(componentBeanDefinition.getCreationMethod().getCreationMethod()));
        // Bean dependencies
        Assert.assertTrue(configBeanDefinition.getBeanDependencies().isEmpty());
        Assert.assertTrue(componentBeanDefinition.getBeanDependencies().stream().count() == 1);
        Assert.assertTrue(componentBeanDefinition.getBeanDependencies().stream()
                .filter(beanDependency -> String.class.equals(beanDependency.getBeanType()))
                .count() == 1);
    }

    @Test(expected = BeanNotFoundException.class)
    public void testGetBeanDefenitionByNameException() throws Exception {
        beanDefinitionRegistry.getBeanDefenitionByName("AnyClass").getClass();
    }

}