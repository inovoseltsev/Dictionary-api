package com.novoseltsev.dicterapi.exception.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {

    @Schema(example = "Http status")
    private String status;

    @Schema(example = "Error message")
    private String message;

    @Schema(example = "123")
    private Integer code;

    public ErrorResponse(HttpStatus status, Exception e) {
        this.status = status.getReasonPhrase();
        this.message = e.getMessage();
        this.code = status.value();
    }
}
