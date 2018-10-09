package com.vleg.spring.exception;

public class RegistryInitiationException extends RuntimeException {

    public RegistryInitiationException() {
    }

    public RegistryInitiationException(String message) {
        super(message);
    }

    public RegistryInitiationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryInitiationException(Throwable cause) {
        super(cause);
    }

    public RegistryInitiationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
