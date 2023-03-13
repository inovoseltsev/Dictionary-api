package com.novoseltsev.dicterapi.validation;

public interface Pattern {

    String NAME_PATTERN = "^[a-zA-Zа-яА-яА-Яа-яёЁЇїІіЄєҐґ ]{1,100}$";

    String LOGIN_PATTERN = "^[a-z0-9_.-]{3,16}$";

    String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,16}$";

    String DESCRIPTION_PATTERN = "^.{0,}$";

    String WORD_DEFINITION_PATTERN = "^[A-Za-zА-Яа-яА-Яа-яёЁЇїІіЄєҐґ ]{1,}$";

    String KEY_WORD_PATTERN = "^[A-Za-zА-Яа-яА-Яа-яёЁЇїІіЄєҐґ ]{0,}$";
}
