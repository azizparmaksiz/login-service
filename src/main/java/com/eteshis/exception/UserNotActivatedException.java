package com.eteshis.exception;

import com.eteshis.core.CommonExceptionMessage;

public class UserNotActivatedException extends RuntimeException {

	private static final long serialVersionUID = 6022337162488272950L;

	private ExceptionMessage exceptionMessage;

	public UserNotActivatedException(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public UserNotActivatedException(String message) {
		this(message, "");
	}

	public UserNotActivatedException(String message, String args) {
		super(message);
		this.exceptionMessage = new ExceptionMessage(message, args);
	}

	public ExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}


}
