package com.novoseltsev.dicterapi.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String status;

    private String message;

    private Integer code;

    public ErrorResponse(HttpStatus status, Exception e) {
        this.status = status.getReasonPhrase();
        this.message = e.getMessage();
        this.code = status.value();
    }
}
