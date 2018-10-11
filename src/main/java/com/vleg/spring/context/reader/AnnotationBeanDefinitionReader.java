package com.vleg.spring.context.reader;

import com.vleg.spring.annotation.Component;
import com.vleg.spring.entity.supplier.AnnotationBeanSupplier;
import com.vleg.spring.entity.supplier.BeanSupplier;
import com.vleg.spring.utils.ComponentScanUtils;
import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationBeanDefinitionReader implements BeanDefinitionReader {

    private static String searchPath = ComponentScanUtils.getApplicationBeanScanPath();
    private static Reflections reflections = new Reflections(searchPath);
    private Set<BeanSupplier> suppliers;

    public static AnnotationBeanDefinitionReader newInstance() {
        AnnotationBeanDefinitionReader result = new AnnotationBeanDefinitionReader();
        result.suppliers = searchBeanCoppliers();
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

    private static Set<BeanSupplier> searchBeanCoppliers() {
        return reflections.getTypesAnnotatedWith(Component.class).stream()
                .map(AnnotationBeanSupplier::new)
                .collect(Collectors.toSet());
    }
}
