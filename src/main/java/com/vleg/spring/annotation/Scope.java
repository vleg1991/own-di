package com.vleg.spring.annotation;


import com.vleg.spring.entity.definition.BeanType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.vleg.spring.entity.definition.BeanType.SINGLTON;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    BeanType name() default SINGLTON;
}
