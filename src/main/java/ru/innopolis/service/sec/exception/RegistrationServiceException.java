package ru.innopolis.service.sec.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.service.exception.ServiceException;

public class RegistrationServiceException extends ServiceException {

    private static final Logger logger = LogManager.getLogger(RegistrationServiceException.class.getName());

    private static final long serialVersionUID = -3761148224264973268L;

    public RegistrationServiceException() {
    }

    public RegistrationServiceException(String message) {
        super(message);
    }

    public RegistrationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationServiceException(String message, Throwable cause, boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RegistrationServiceException(Throwable cause) {
        super(cause);
    }
}
