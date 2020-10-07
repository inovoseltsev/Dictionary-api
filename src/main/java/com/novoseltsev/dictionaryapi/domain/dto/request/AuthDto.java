package com.novoseltsev.dictionaryapi.domain.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
