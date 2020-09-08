package com.novoseltsev.dictionaryapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.domain.role.UserRole;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;


import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.FIRST_NAME_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.LAST_NAME_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.LOGIN_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.LOGIN_PATTERN;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.NAME_PATTERN;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.PASSWORD_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.PASSWORD_PATTERN;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.USER_ROLE_ERROR;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpUserDto {

    @NotBlank(message = FIRST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = FIRST_NAME_ERROR)
    private String firstName;

    @NotBlank(message = LAST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = LAST_NAME_ERROR)
    private String lastName;

    @NotBlank(message = LOGIN_ERROR)
    @Pattern(regexp = LOGIN_PATTERN, message = LOGIN_ERROR)
    private String login;

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String password;

    @NotNull(message = USER_ROLE_ERROR)
    private UserRole role;

    public User toUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
