package com.eteshis.exception;


public class UnAuthorizedOperationException extends RuntimeException {

    private ExceptionMessage exceptionMessage;

    public UnAuthorizedOperationException(ExceptionMessage exceptionMessage) {
         this.exceptionMessage = exceptionMessage;
    }

    public UnAuthorizedOperationException(String message) {
        this(message, "");
    }

    public UnAuthorizedOperationException(String message, String args) {
        super(message);
         this.exceptionMessage = new ExceptionMessage(message, args);
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

}
