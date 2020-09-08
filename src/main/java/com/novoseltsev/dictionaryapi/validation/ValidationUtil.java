package com.novoseltsev.dictionaryapi.validation;

public class ValidationUtil {

    public static final String NAME_PATTERN =
            "^[a-zA-Zа-яА-я]{1,100}$";

    public static final String LOGIN_PATTERN =
            "^[a-z0-9_.-]{3,16}$";

    public static final String PASSWORD_PATTERN =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16}$";

    private static final String BASE_MESSAGE_PART = " is not correct!";

    public static final String FIRST_NAME_ERROR =
            "User last name" + BASE_MESSAGE_PART;

    public static final String LAST_NAME_ERROR =
            "User first name" + BASE_MESSAGE_PART;

    public static final String LOGIN_ERROR =
            "User login" + BASE_MESSAGE_PART;

    public static final String PASSWORD_ERROR =
            "User password" + BASE_MESSAGE_PART;

    public static final String USER_ROLE_ERROR =
            "User role" + BASE_MESSAGE_PART;
}
