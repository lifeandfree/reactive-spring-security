package ru.innopolis.service.sec.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrganizationNotActiveAuthenticationException extends RuntimeException {

    private static final long serialVersionUID = -2632474386723128117L;

    private static final Logger logger = LogManager.getLogger(OrganizationNotActiveAuthenticationException.class.getName());

    public OrganizationNotActiveAuthenticationException() {
    }

    public OrganizationNotActiveAuthenticationException(String message) {
        super(message);
    }

    public OrganizationNotActiveAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrganizationNotActiveAuthenticationException(String message, Throwable cause, boolean enableSuppression,
                                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OrganizationNotActiveAuthenticationException(Throwable cause) {
        super(cause);
    }
}
