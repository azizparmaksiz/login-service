package com.todo.exception;

/**
 * Created by Dell on 21.05.2018.
 */
public class AccessException extends RuntimeException {
    private ExceptionMessage exceptionMessage;

    public AccessException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public AccessException(String message) {
        this(message, "");
    }

    public AccessException(String message, String args) {
        super(message);
        this.exceptionMessage = new ExceptionMessage(message, args);
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

}