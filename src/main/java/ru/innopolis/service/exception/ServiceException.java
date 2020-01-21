package ru.innopolis.service.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceException extends RuntimeException {

	private static final Logger logger = LogManager.getLogger(ServiceException.class.getName());

	private static final long serialVersionUID = 7937462387683161876L;
	private String errorCode;
	private String errorMessage;

	public ServiceException() {
		super();
	}

	public ServiceException(String errorMessage) {
		super(errorMessage);
	}

	public ServiceException(String errorCode, String errorMessage) {
		super(errorCode + ": " + errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ServiceException(String errorMessage, Throwable rootCause) {

		super(errorMessage, rootCause);
	}

	public ServiceException(String errorMessage, Throwable cause, boolean enableSuppression,
                            boolean writableStackTrace) {
		super(errorMessage, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
