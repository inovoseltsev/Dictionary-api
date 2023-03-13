package com.novoseltsev.dicterapi.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dicterapi.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static com.novoseltsev.dicterapi.validation.Pattern.LOGIN_PATTERN;
import static com.novoseltsev.dicterapi.validation.Pattern.NAME_PATTERN;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.FIRST_NAME_ERROR;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.LAST_NAME_ERROR;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.LOGIN_ERROR;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    @NotNull
    @Positive
    @Schema(example = "123")
    private Long id;

    @NotBlank(message = FIRST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = FIRST_NAME_ERROR)
    @Schema(example = "Illia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank(message = LAST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = LAST_NAME_ERROR)
    @Schema(example = "Novoseltsev", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @NotBlank(message = LOGIN_ERROR)
    @Pattern(regexp = LOGIN_PATTERN, message = LOGIN_ERROR)
    @Schema(example = "login", requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;

    public User toEntity() {
        var user = new User(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        return user;
    }

    public static UserDto from(User user) {
        return new UserDto(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getLogin()
        );
    }
}
