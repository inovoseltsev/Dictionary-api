package com.novoseltsev.dicterapi.controller.v1.api;

import com.novoseltsev.dicterapi.domain.dto.auth.PasswordDto;
import com.novoseltsev.dicterapi.domain.dto.user.AdminUserDto;
import com.novoseltsev.dicterapi.domain.dto.user.SignUpUserDto;
import com.novoseltsev.dicterapi.domain.dto.user.UserDto;
import com.novoseltsev.dicterapi.domain.status.UserStatus;
import com.novoseltsev.dicterapi.exception.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "user", description = "User API")
public interface UserApi {

    @Operation(summary = "Get user by id", tags = "user")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User",
            content = {
                @Content(
                    schema = @Schema(implementation = UserDto.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
                )
            }),
        @ApiResponse(responseCode = "400", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "User token is not valid"),
        @ApiResponse(responseCode = "403", description = "User status is not active"),
    })
    UserDto findById(@Parameter(description = "User id", required = true) Long id);

    @Operation(summary = "Get all non-admin users", tags = "user")
    @ApiResponse(
        responseCode = "200",
        description = "List of all non-admin users",
        content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = AdminUserDto.class)),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    List<AdminUserDto> findAll();

    @Operation(summary = "User registration", tags = "user")
    @RequestBody(
        description = "User registration data",
        required = true,
        content = @Content(
            schema = @Schema(implementation = SignUpUserDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        responseCode = "201",
        description = "Created user",
        content = {
            @Content(
                schema = @Schema(implementation = UserDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    @ApiResponse(
        responseCode = "400",
        description = "User data validation failed",
        content = {
            @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    @ApiResponse(
        responseCode = "409",
        description = "User login is already used",
        content = {
            @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    ResponseEntity<UserDto> create(SignUpUserDto signUpUserDto);

    @Operation(summary = "Update user data", tags = "user")
    @RequestBody(
        description = "User with updated data",
        required = true,
        content = @Content(
            schema = @Schema(implementation = UserDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "User data validation failed or user not found",
        content = {
            @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    @ApiResponse(
        responseCode = "403",
        description = "User status is not active",
        content = {
            @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    UserDto update(UserDto userDto);

    @Operation(summary = "Update user password", tags = "user")
    @RequestBody(
        description = "Old password to verify and new password to update",
        required = true,
        content = @Content(
            schema = @Schema(implementation = UserDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    void updatePassword(@Parameter(description = "User id", required = true) Long id, PasswordDto passwordDto);

    void updateUserStatus(@Parameter(description = "User id", required = true) Long id, UserStatus status);

    ResponseEntity<HttpStatus> deleteById(@Parameter(description = "User id", required = true) Long id);
}
