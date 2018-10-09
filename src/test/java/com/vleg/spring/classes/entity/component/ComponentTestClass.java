package com.vleg.spring.classes.entity.component;

import com.vleg.spring.annotation.Autowired;
import com.vleg.spring.annotation.Component;
import com.vleg.spring.annotation.Scope;
import com.vleg.spring.entity.definition.BeanType;

@Component
@Scope(name = BeanType.PROTOTYPE)
public class ComponentTestClass {

    private String value;

    @Autowired
    public ComponentTestClass(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
