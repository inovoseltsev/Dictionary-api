package com.novoseltsev.dictionaryapi.exception.util;

import com.novoseltsev.dictionaryapi.exception.ObjectNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public final class ExceptionUtils {

    public static final Supplier<RuntimeException> OBJECT_NOT_FOUND = () -> {
        throw new ObjectNotFoundException(MessageCause.OBJECT_NOT_FOUND);
    };

    public static Map<String, String> handleValidationErrors(
            MethodArgumentNotValidException e) {
        return new HashMap<String, String>() {{
            e.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                put(fieldName, errorMessage);
            });
        }};
    }

    public static Map<Object, Object> getErrorResponse(Exception e) {
        return new HashMap<Object, Object>() {{
            put("error", e.getMessage());
        }};
    }
}
