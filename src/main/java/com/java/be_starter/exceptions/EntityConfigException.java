package com.java.be_starter.exceptions;

public class EntityConfigException extends SaveEntityException{
    public EntityConfigException(Throwable cause) {
        super("Entity save conflict", cause);
    }
}
