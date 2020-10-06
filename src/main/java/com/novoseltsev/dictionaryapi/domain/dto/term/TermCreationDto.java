package com.novoseltsev.dictionaryapi.domain.dto.term;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TermCreationDto extends AbstractTermDto {

    @Positive
    @NotNull
    private Long termGroupId;

    @Override
    public Term toEntity() {
        Term term = super.toEntity();
        term.setTermGroup(new TermGroup(termGroupId));
        return term;
    }
}