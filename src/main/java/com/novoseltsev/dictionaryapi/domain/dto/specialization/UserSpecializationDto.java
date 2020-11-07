package com.novoseltsev.dictionaryapi.domain.dto.specialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSpecializationDto extends AbstractSpecializationDto {

    private Long userId;

    public UserSpecializationDto(String name, String description) {
        super(name, description);
    }
}
