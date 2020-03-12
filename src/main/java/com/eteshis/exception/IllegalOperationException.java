package com.eteshis.exception;

/**
 * For HTTP 404 errros
 */
public class IllegalOperationException extends RuntimeException {
    private ExceptionMessage exceptionMessage;

    public IllegalOperationException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public IllegalOperationException(String message) {
        this(message, "");
    }

    public IllegalOperationException(String message, String args) {
        super(message);
        this.exceptionMessage = new ExceptionMessage(message, args);
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }



}
