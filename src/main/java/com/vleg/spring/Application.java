package com.vleg.spring;

import com.vleg.spring.annotation.BeanScan;
import com.vleg.spring.context.ApplicationContext;

@BeanScan
public class Application {

    public static void main(String[] args) {
        new ApplicationContext();
    }
}