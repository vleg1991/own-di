package com.vleg.spring.utils;

import com.vleg.spring.annotation.BeanScan;
import com.vleg.spring.exception.ApplicationInitiationException;

public class ComponentScanUtils {

    /**
     *
     * @return reference to specified for scaning package or <br/>default reference to package contains main class
     *
     * */
    public static String getApplicationBeanScanPath() {
        try {
            Class mainClass = Class.forName(System.getProperty("sun.java.command"));
            Class returnType = mainClass.getMethod("main", String[].class).getReturnType();
            if ("void".toUpperCase().equals(returnType.getName().toUpperCase())) {
                String searchPath = ((BeanScan) mainClass.getAnnotation(BeanScan.class)).path();
                if ("".equals(searchPath))
                    return System.getProperty("sun.java.command").replace("." + mainClass.getSimpleName(), "");
                else
                    return searchPath;
            }
        } catch (ClassNotFoundException e) {
            throw new ApplicationInitiationException("Cannot find main class. Try to use OpenJDK.");
        } catch (NoSuchMethodException e) {
            throw new ApplicationInitiationException("Cannot find method \"main\"");
        } catch (Exception e) {
            throw new ApplicationInitiationException("Method \"main\" does not void return type, or check ComponentScan annotation for specifying component and configuration class for search");
        }
        return null;
    }
}
