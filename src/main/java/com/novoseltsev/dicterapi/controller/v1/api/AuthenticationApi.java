package com.novoseltsev.dicterapi.controller.v1.api;

import com.novoseltsev.dicterapi.domain.dto.auth.AuthenticationDto;
import com.novoseltsev.dicterapi.exception.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.MediaType;

@Tag(name = "authentication", description = "Api to get JWT and authorize in app")
public interface AuthenticationApi {


    @Operation(summary = "Use login and password to authenticate user and create JWT", tags = "authentication")
    @RequestBody(
        required = true,
        content = @Content(
            schema = @Schema(implementation = AuthenticationDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        ),
        description = "User's login and password"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Json Web Token (JWT)",
        content = {
            @Content(
                schema = @Schema(implementation = Map.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    @ApiResponse(
        responseCode = "401",
        description = "User credentials are wrong",
        content = {
            @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    String authenticate(AuthenticationDto authenticationDto);
}
