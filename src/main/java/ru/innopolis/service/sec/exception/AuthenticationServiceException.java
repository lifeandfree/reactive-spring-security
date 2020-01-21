package ru.innopolis.service.sec.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticationServiceException extends RuntimeException {

    private static final Logger logger = LogManager.getLogger(AuthenticationServiceException.class.getName());

    private static final long serialVersionUID = -7474813574263616455L;

    public AuthenticationServiceException() {
    }

    public AuthenticationServiceException(String message) {
        super(message);
    }

    public AuthenticationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationServiceException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthenticationServiceException(Throwable cause) {
        super(cause);
    }
}
