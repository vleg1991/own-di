package com.vleg.spring.exception;

public class BeanTypeDuplicateException extends RuntimeException {

    public BeanTypeDuplicateException() {
    }

    public BeanTypeDuplicateException(String message) {
        super(message);
    }

    public BeanTypeDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanTypeDuplicateException(Throwable cause) {
        super(cause);
    }

    public BeanTypeDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
