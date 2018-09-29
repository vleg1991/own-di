package com.vleg.spring.annotation;


import com.vleg.spring.entity.BeanType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.vleg.spring.entity.BeanType.SINGLTON;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    BeanType name() default SINGLTON;
}
