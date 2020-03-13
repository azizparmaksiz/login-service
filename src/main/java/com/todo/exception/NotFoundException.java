package com.todo.exception;

public class NotFoundException extends RuntimeException {
    private ExceptionMessage exceptionMessage;

    public NotFoundException(ExceptionMessage exceptionMessage) {
         this.exceptionMessage = exceptionMessage;
    }

    public NotFoundException(String message) {
        this(message, "");
    }

    public NotFoundException(String message, String args) {
        super(message);
         this.exceptionMessage = new ExceptionMessage(message, args);
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

}
