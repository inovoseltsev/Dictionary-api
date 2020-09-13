package com.novoseltsev.dictionaryapi.exception;

public class InvalidOldPasswordException extends RuntimeException {

    public InvalidOldPasswordException() {
    }

    public InvalidOldPasswordException(String message) {
        super(message);
    }

    public InvalidOldPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
