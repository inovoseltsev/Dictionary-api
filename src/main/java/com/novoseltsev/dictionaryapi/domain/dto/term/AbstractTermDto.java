package com.novoseltsev.dictionaryapi.domain.dto.term;

import com.novoseltsev.dictionaryapi.domain.dto.DtoMapper;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import javax.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class AbstractTermDto implements DtoMapper<Term> {

    @NotBlank
    private String name;

    @NotBlank
    private String definition;

    public AbstractTermDto(String name, String definition) {
        this.name = name;
        this.definition = definition;
    }

    @Override
    public Term toEntity() {
        return new Term(name, definition);
    }
}
