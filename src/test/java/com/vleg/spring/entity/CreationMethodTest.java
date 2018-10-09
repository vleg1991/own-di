package com.vleg.spring.entity;

import com.vleg.spring.classes.TestClass;
import com.vleg.spring.entity.definition.CreationMethod;
import com.vleg.spring.entity.definition.CreationType;
import com.vleg.spring.exception.BeanCreationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreationMethodTest {

    @Test
    public void invokeMethodCreation() throws Exception {
        CreationMethod creationMethod = new CreationMethod(TestClass.class.getMethod("testMethod", String.class), CreationType.METHOD);
        assertEquals(creationMethod.invoke("test_name"), new TestClass("test_name"));
    }

    @Test
    public void invokeConstuctorCreation() throws Exception {
        CreationMethod creationMethod = new CreationMethod(TestClass.class.getConstructor(String.class), CreationType.CONSTRUCTOR);
        assertEquals(creationMethod.invoke("test_name"), new TestClass("test_name"));
    }

    @Test(expected = BeanCreationException.class)
    public void invokeMethodCreationWithException() throws Exception {
        CreationMethod creationMethod = new CreationMethod(TestClass.class.getMethod("testMethod"), CreationType.METHOD);
        creationMethod.invoke();
    }

}
