package ru.innopolis.service.sec.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotUserOrPasswordAuthenticationException extends AuthenticationServiceException {

    private static final long serialVersionUID = -2632474386723128117L;

    private static final Logger logger = LogManager.getLogger(NotUserOrPasswordAuthenticationException.class.getName());

    public NotUserOrPasswordAuthenticationException() {
    }

    public NotUserOrPasswordAuthenticationException(String message) {
        super(message);
    }

    public NotUserOrPasswordAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUserOrPasswordAuthenticationException(String message, Throwable cause, boolean enableSuppression,
                                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NotUserOrPasswordAuthenticationException(Throwable cause) {
        super(cause);
    }
}
