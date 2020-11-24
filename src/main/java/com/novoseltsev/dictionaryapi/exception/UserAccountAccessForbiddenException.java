package com.novoseltsev.dictionaryapi.exception;

public class UserAccountAccessForbiddenException extends RuntimeException {

    public UserAccountAccessForbiddenException() {
        super();
    }

    public UserAccountAccessForbiddenException(String message) {
        super(message);
    }

    public UserAccountAccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
