package com.novoseltsev.dicterapi.exception;

public class UserAccessForbiddenException extends RuntimeException {

    public UserAccessForbiddenException() {
        super();
    }

    public UserAccessForbiddenException(String message) {
        super(message);
    }

    public UserAccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
