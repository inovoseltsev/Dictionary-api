package com.novoseltsev.dicterapi.exception.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public final class ExceptionUtils {

    public static Map<String, String> handleValidationErrors(MethodArgumentNotValidException e) {
        return new HashMap<>() {{
            e.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                put(fieldName, errorMessage);
            });
        }};
    }

    public static Map<Object, Object> getErrorResponse(Exception e) {
        return new HashMap<>() {{
            put("error", e.getMessage());
        }};
    }
}
