package com.java.be_starter.exceptions;

public class SaveEntityException extends RuntimeException {
    public SaveEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
