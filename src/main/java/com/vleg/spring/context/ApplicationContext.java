package com.vleg.spring.context;

public class ApplicationContext {

    private final BeanFactory beanFactory = new BeanFactory();

    public void refresh() {
        beanFactory.loadSingltonBeansToRegistry();
    }

    /**
     *
     * Проверяет все зависиомости на возможность создания и достаточность зависимостей
     *
     * */
    public void validateDependecies () {

    }

    /**
     *
     * Запуск приложения
     *
     * */
    public void run() {

    }
}
