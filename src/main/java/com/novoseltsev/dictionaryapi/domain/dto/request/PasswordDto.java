package com.novoseltsev.dictionaryapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;


import static com.novoseltsev.dictionaryapi.validation.Pattern.PASSWORD_PATTERN;
import static com.novoseltsev.dictionaryapi.validation.ValidationMessage.PASSWORD_ERROR;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordDto {

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String oldPassword;

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String newPassword;
}
