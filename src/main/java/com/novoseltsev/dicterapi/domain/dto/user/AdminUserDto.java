package com.novoseltsev.dicterapi.domain.dto.user;

import com.novoseltsev.dicterapi.domain.entity.User;
import com.novoseltsev.dicterapi.domain.status.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AdminUserDto extends UserDto {

    @Schema(example = "login")
    private final String login;

    @Schema(example = "ACTIVE")
    private final UserStatus status;

    public AdminUserDto(Long id, String firstName, String lastName, String login, UserStatus status) {
        super(id, firstName, lastName);
        this.login = login;
        this.status = status;
    }

    public static AdminUserDto from(User user) {
        return new AdminUserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getStatus()
        );
    }
}
