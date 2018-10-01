package com.vleg.spring.exception;

public class BeanResolveException extends RuntimeException {

    public BeanResolveException() {
    }

    public BeanResolveException(String message) {
        super(message);
    }

    public BeanResolveException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanResolveException(Throwable cause) {
        super(cause);
    }

    public BeanResolveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
