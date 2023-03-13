package com.novoseltsev.dicterapi.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
