package com.novoseltsev.dicterapi.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dicterapi.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "123")
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
