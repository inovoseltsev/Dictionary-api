package com.novoseltsev.dicterapi.domain.dto.user;

import com.novoseltsev.dicterapi.domain.entity.User;
import com.novoseltsev.dicterapi.domain.status.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUserDto {

    @Schema(example = "123")
    private Long id;

    @Schema(example = "Illia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @Schema(example = "Novoseltsev", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Schema(example = "login")
    private final String login;

    @Schema(example = "ACTIVE")
    private final UserStatus status;

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
