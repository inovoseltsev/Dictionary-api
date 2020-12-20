package com.novoseltsev.dictionaryapi.domain.dto.user;

import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.domain.status.UserStatus;
import lombok.Getter;

@Getter
public class AdminUserDto extends UserDto {

    private final String login;

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
