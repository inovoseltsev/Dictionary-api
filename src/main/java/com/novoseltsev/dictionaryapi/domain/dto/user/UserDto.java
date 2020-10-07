package com.novoseltsev.dictionaryapi.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends AbstractUserDto {

    @NotNull
    @Positive
    private Long id;

    public UserDto(Long id, String firstName, String lastName) {
        super(firstName, lastName);
        this.id = id;
    }

    @Override
    public User toEntity() {
        User user = super.toEntity();
        user.setId(id);
        return user;
    }

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
