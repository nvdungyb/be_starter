package com.java.be_starter.exceptions;

public class InvalidEntityException extends SaveEntityException {
    public InvalidEntityException(Throwable cause) {
        super("Invalid entity data", cause);
    }
}
