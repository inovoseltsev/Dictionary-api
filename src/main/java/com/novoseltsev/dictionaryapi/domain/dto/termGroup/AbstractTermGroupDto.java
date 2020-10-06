package com.novoseltsev.dictionaryapi.domain.dto.termGroup;

import com.novoseltsev.dictionaryapi.domain.dto.DtoMapper;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.NoArgsConstructor;


import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.DESCRIPTION_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.DESCRIPTION_PATTERN;

@NoArgsConstructor
public abstract class AbstractTermGroupDto implements DtoMapper<TermGroup> {

    @NotBlank
    private String name;

    @Pattern(regexp = DESCRIPTION_PATTERN, message = DESCRIPTION_ERROR)
    private String description;

    public AbstractTermGroupDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public TermGroup toEntity() {
        return new TermGroup(name, description);
    }
}
