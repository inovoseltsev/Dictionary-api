package com.novoseltsev.dicterapi.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dicterapi.domain.entity.User;
import com.novoseltsev.dicterapi.domain.role.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import static com.novoseltsev.dicterapi.validation.Pattern.LOGIN_PATTERN;
import static com.novoseltsev.dicterapi.validation.Pattern.PASSWORD_PATTERN;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.LOGIN_ERROR;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.PASSWORD_ERROR;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.USER_ROLE_ERROR;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpUserDto extends AbstractUserDto {

    @NotBlank(message = LOGIN_ERROR)
    @Pattern(regexp = LOGIN_PATTERN, message = LOGIN_ERROR)
    @Schema(example = "login", requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;

    @NotBlank(message = PASSWORD_ERROR)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR)
    @Schema(example = "Password12", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotNull(message = USER_ROLE_ERROR)
    @Schema(example = "ADMIN", requiredMode = Schema.RequiredMode.REQUIRED)
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
