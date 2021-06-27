package com.joon.imageshopthymeleaf.common.exception;

public class NotFoundUser extends RuntimeException {
    public NotFoundUser(String message) {
        super(message);
    }
}
