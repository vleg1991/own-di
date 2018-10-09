package com.vleg.spring.classes;

import com.vleg.spring.annotation.Autowired;
import com.vleg.spring.annotation.Component;

@Component
public class AnnotatedTestClassWithConstructor {

    @Autowired
    public AnnotatedTestClassWithConstructor(String inputString) {
    }
}
