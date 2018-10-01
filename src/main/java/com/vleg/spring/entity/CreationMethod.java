package com.vleg.spring.entity;

import com.vleg.spring.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class CreationMethod {

    private final Executable creationMethod;
    private final CreationType creationType;

    public CreationMethod(Executable creationMethod, CreationType creationType) {
        this.creationMethod = creationMethod;
        this.creationType = creationType;
    }

    public Executable getCreationMethod() {
        return creationMethod;
    }

    public CreationType getCreationType() {
        return creationType;
    }

    public Object invoke(Object invokableInstance, Object... args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Object result = null;
        if (CreationType.METHOD.equals(this.creationType))
            result = ((Method) creationMethod).invoke(invokableInstance, args);
        if (CreationType.CONSTRUCTOR.equals(this.creationType))
            result = ((Constructor) creationMethod).newInstance(invokableInstance, args);
        if (Objects.nonNull(result))
            return result;
        else
            throw new BeanCreationException("Check creation logic");
    }
}
