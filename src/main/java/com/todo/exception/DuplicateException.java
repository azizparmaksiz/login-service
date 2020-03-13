package com.todo.exception;

/**
 * For HTTP 404 errros
 */
public class DuplicateException extends RuntimeException {

	private ExceptionMessage exceptionMessage;

	public DuplicateException(ExceptionMessage exceptionMessage) {
		 this.exceptionMessage = exceptionMessage;
	}

	public DuplicateException(String message) {
		this(message, "");
	}

	public DuplicateException(String message, String args) {
		super(message);
		 this.exceptionMessage = new ExceptionMessage(message, args);
	}

	public ExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}


}
