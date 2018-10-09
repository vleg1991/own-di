package com.vleg.spring.entity.definition;

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

    public Object invoke(Object... args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Object result = null;
        if (CreationType.METHOD.equals(this.creationType)) {
            Method method = ((Method) creationMethod);
            Object invokableInstance = method.getDeclaringClass().newInstance();
            result = method.invoke(invokableInstance, args);
        }
        if (CreationType.CONSTRUCTOR.equals(this.creationType))
            result = ((Constructor) creationMethod).newInstance(args);
        if (Objects.nonNull(result))
            return result;
        else
            throw new BeanCreationException("Check creation logic");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreationMethod)) return false;
        CreationMethod that = (CreationMethod) o;
        return Objects.equals(creationMethod, that.creationMethod) &&
                creationType == that.creationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationMethod, creationType);
    }
}
