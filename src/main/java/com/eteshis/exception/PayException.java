package com.eteshis.exception;

/**
 * For HTTP 404 errros
 */
public class PayException extends RuntimeException {

    private ExceptionMessage exceptionMessage;

    public PayException(ExceptionMessage exceptionMessage) {
         this.exceptionMessage = exceptionMessage;
    }

    public PayException(String message) {
        this(message, "");
    }

    public PayException(String message, String args) {
        super(message);
         this.exceptionMessage = new ExceptionMessage(message, args);
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

}
