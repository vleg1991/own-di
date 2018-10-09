package com.vleg.spring.context;

import com.vleg.spring.context.reader.AnnotationBeanDefinitionReader;
import com.vleg.spring.context.reader.BeanDefinitionReader;
import com.vleg.spring.context.reader.JavaConfigBeanDefinitionReader;
import com.vleg.spring.entity.definition.BeanDefinition;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanDefinitionBuilder {

    private JavaConfigBeanDefinitionReader javaConfigBeanDefinitionReader = JavaConfigBeanDefinitionReader.newInstance();
    private AnnotationBeanDefinitionReader annotationBeanDefinitionReader = AnnotationBeanDefinitionReader.newInstance();

    public static BeanDefinitionBuilder newInstance() {
        return new BeanDefinitionBuilder();
    }

    public Set<BeanDefinition> getBeanDefinitions() {
        Set<BeanDefinition> result = new HashSet<>();
        result.addAll(getJavaConfigBeanDefinitions());
        result.addAll(getAnnotationBeanDefinitions());

//        BeanUtils.assertNonDuplicateBeans(result);

        return result;
    }

    public Set<BeanDefinition> getJavaConfigBeanDefinitions() {
        return getBeanDefinitionsBy(javaConfigBeanDefinitionReader);
    }

    public Set<BeanDefinition> getAnnotationBeanDefinitions() {
        return getBeanDefinitionsBy(annotationBeanDefinitionReader);
    }

    private Set<BeanDefinition> getBeanDefinitionsBy(BeanDefinitionReader beanDefinitionReader) {
        return beanDefinitionReader.getSuppliers().stream()
                .map(beanSupplier ->
                        new BeanDefinition(
                                beanSupplier.getBeanName(),
                                beanSupplier.getBeanType(),
                                beanSupplier.getCreationMethod()
                        )
                )
                .collect(Collectors.toSet());
    }
}
