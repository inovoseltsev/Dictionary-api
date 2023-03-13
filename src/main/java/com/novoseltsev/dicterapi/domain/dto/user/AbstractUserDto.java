package com.novoseltsev.dicterapi.domain.dto.user;

import com.novoseltsev.dicterapi.domain.dto.DtoMapper;
import com.novoseltsev.dicterapi.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static com.novoseltsev.dicterapi.validation.Pattern.NAME_PATTERN;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.FIRST_NAME_ERROR;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.LAST_NAME_ERROR;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractUserDto implements DtoMapper<User> {

    @NotBlank(message = FIRST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = FIRST_NAME_ERROR)
    @Schema(example = "Illia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank(message = LAST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = LAST_NAME_ERROR)
    @Schema(example = "Novoseltsev", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    public AbstractUserDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public User toEntity() {
        return new User(firstName, lastName);
    }
}
