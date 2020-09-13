package com.novoseltsev.dictionaryapi.exception.util;

public interface MessageCause {

    String OBJECT_NOT_FOUND = "Object not found";

    String LOGIN_IS_ALREADY_USED = "Such login is already used";

    String BAD_TOKEN = "Token is expired or invalid";

    String INVALID_OLD_PASSWORD = "Old password is incorrect";

    String BAD_CREDENTIALS = "Login or password is invalid";
}
