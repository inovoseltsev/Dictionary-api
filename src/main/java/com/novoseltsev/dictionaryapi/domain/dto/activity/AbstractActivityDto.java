package com.novoseltsev.dictionaryapi.domain.dto.activity;

import com.novoseltsev.dictionaryapi.domain.dto.DtoMapper;
import com.novoseltsev.dictionaryapi.domain.entity.Activity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;


import static com.novoseltsev.dictionaryapi.validation.Pattern.DESCRIPTION_PATTERN;
import static com.novoseltsev.dictionaryapi.validation.ValidationMessage.DESCRIPTION_ERROR;

@Getter
@NoArgsConstructor
public abstract class AbstractActivityDto implements DtoMapper<Activity> {

    @NotBlank
    String name;

    @Pattern(regexp = DESCRIPTION_PATTERN, message = DESCRIPTION_ERROR)
    String description;

    public AbstractActivityDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public Activity toEntity() {
        return new Activity(name, description);
    }
}
