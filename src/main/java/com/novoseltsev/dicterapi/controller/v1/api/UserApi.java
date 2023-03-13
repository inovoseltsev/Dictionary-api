package com.novoseltsev.dicterapi.controller.v1.api;

import com.novoseltsev.dicterapi.domain.dto.auth.PasswordDto;
import com.novoseltsev.dicterapi.domain.dto.user.AdminUserDto;
import com.novoseltsev.dicterapi.domain.dto.user.SignUpUserDto;
import com.novoseltsev.dicterapi.domain.dto.user.UserDto;
import com.novoseltsev.dicterapi.domain.status.UserStatus;
import com.novoseltsev.dicterapi.exception.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "user", description = "User API")
public interface UserApi {

    @Operation(summary = "Get user by id", tags = "user")
    @ApiResponse(
        responseCode = "200",
        description = "User",
        content = {
            @Content(
                schema = @Schema(implementation = UserDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    @ApiResponse(
        responseCode = "400",
        description = "User not found",
        content = {
            @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    @ApiResponse(
        responseCode = "401",
        description = "Token is not valid",
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
    UserDto findById(@Parameter(description = "User id", required = true) Long id);

    @Operation(summary = "Get all users", tags = "user")
    @ApiResponse(
        responseCode = "200",
        description = "User",
        content = {
            @Content(
                schema = @Schema(implementation = AdminUserDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    @ApiResponse(
        responseCode = "401",
        description = "Token is not valid",
        content = {
            @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    List<AdminUserDto> findAll();

    @PostMapping("/registration")
    ResponseEntity<UserDto> create(@Valid SignUpUserDto signUpUserDto);

    @PutMapping("/{id}")
    UserDto update(@Valid @RequestBody UserDto userDto);

    @PutMapping("/password/{id}")
    void updatePassword(@PathVariable Long id, PasswordDto passwordDto);

    @PutMapping("/user-status/{id}")
    void updateUserStatus(@PathVariable Long id, UserStatus status);

    @DeleteMapping("/full-deletion/{id}")
    ResponseEntity<HttpStatus> deleteById(@PathVariable Long id);
}
