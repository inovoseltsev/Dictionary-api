package com.novoseltsev.dictionaryapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;


import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.PASSWORD_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.PASSWORD_PATTERN;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordDto {

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String oldPassword;

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    private String newPassword;
}
