package com.novoseltsev.dicterapi.exception;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException() {
    }

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
