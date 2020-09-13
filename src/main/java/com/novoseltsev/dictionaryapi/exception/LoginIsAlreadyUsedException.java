package com.novoseltsev.dictionaryapi.exception;

public class LoginIsAlreadyUsedException extends RuntimeException {

    public LoginIsAlreadyUsedException() {
    }

    public LoginIsAlreadyUsedException(String message) {
        super(message);
    }

    public LoginIsAlreadyUsedException(String message, Throwable cause) {
        super(message, cause);
    }
}
