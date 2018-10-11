package com.vleg.spring.context.reader;

import com.vleg.spring.annotation.Bean;
import com.vleg.spring.annotation.Configuration;
import com.vleg.spring.entity.supplier.BeanSupplier;
import com.vleg.spring.entity.supplier.JavaConfigBeanSupplier;
import com.vleg.spring.utils.ComponentScanUtils;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JavaConfigBeanDefinitionReader implements BeanDefinitionReader {

    private static String searchPath = ComponentScanUtils.getApplicationBeanScanPath();
    private static Reflections reflections = new Reflections(searchPath);
    private Set<BeanSupplier> suppliers;

    public static JavaConfigBeanDefinitionReader newInstance() {
        JavaConfigBeanDefinitionReader result = new JavaConfigBeanDefinitionReader();
        result.suppliers = searchBeanSuppliers();
        return result;
    }

    @Override
    public Set<BeanSupplier> getSuppliers() {
        return suppliers;
    }

    @Override
    public BeanSupplier getSupplierByBeanName(String beanName) {
        if ("".equals(beanName) || beanName == null)
            return null;
        return suppliers.stream().findFirst().get();
    }

    private static Set<BeanSupplier> searchBeanSuppliers() {

        Set<BeanSupplier> result = new HashSet<>();

        Set<Class<?>> configurationClasses = reflections.getTypesAnnotatedWith(Configuration.class);
        for (Class aClass : configurationClasses) {
            Set<BeanSupplier> beanSuppliers = Arrays.stream(aClass.getMethods())
                    .filter(method -> method.isAnnotationPresent(Bean.class))
                    .map(JavaConfigBeanSupplier::new)
                    .collect(Collectors.toSet());
            result.addAll(beanSuppliers);
        }

        return result;
    }




}
