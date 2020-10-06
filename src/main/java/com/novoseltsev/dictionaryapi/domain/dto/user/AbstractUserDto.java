package com.novoseltsev.dictionaryapi.domain.dto.user;

import com.novoseltsev.dictionaryapi.domain.dto.DtoMapper;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.FIRST_NAME_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.LAST_NAME_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.NAME_PATTERN;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractUserDto implements DtoMapper<User> {

    @NotBlank(message = FIRST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = FIRST_NAME_ERROR)
    private String firstName;

    @NotBlank(message = LAST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = LAST_NAME_ERROR)
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
