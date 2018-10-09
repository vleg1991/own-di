package com.vleg.spring.classes.entity.config;

import com.vleg.spring.annotation.Bean;
import com.vleg.spring.annotation.Configuration;
import com.vleg.spring.annotation.Qualifier;
import com.vleg.spring.annotation.Scope;
import com.vleg.spring.classes.entity.component.ComponentTestClassSecifiedBeanName;
import com.vleg.spring.entity.definition.BeanType;

@Configuration
public class ConfigurationTestClass {

    @Bean
    public String getStringBean(@Qualifier(beanName = "componentBean") ComponentTestClassSecifiedBeanName componentTestClass) {
        return "Test string";
    }

    @Bean(name = "namedStringBean")
    public String getNamedStringBean() {
        return "Named String Bean";
    }

    @Bean
    @Scope(name = BeanType.PROTOTYPE)
    public Long getPrototypeStringBean() {
        return Long.MIN_VALUE;
    }
}
