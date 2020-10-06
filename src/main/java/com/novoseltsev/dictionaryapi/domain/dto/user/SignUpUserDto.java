package com.novoseltsev.dictionaryapi.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.domain.role.UserRole;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.LOGIN_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.LOGIN_PATTERN;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.PASSWORD_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.PASSWORD_PATTERN;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.USER_ROLE_ERROR;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpUserDto extends AbstractUserDto {

    @NotBlank(message = LOGIN_ERROR)
    @Pattern(regexp = LOGIN_PATTERN, message = LOGIN_ERROR)
    private String login;

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String password;

    @NotNull(message = USER_ROLE_ERROR)
    private UserRole role;

    @Override
    public User toEntity() {
        User user = super.toEntity();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
