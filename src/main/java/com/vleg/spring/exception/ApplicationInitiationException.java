package com.vleg.spring.exception;

public class ApplicationInitiationException extends RuntimeException {

    public ApplicationInitiationException() {
    }

    public ApplicationInitiationException(String message) {
        super(message);
    }

    public ApplicationInitiationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationInitiationException(Throwable cause) {
        super(cause);
    }

    public ApplicationInitiationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
