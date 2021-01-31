package com.novoseltsev.dictionaryapi.domain.dto.term;

import com.novoseltsev.dictionaryapi.domain.entity.Term;
import lombok.Getter;

@Getter
public class AnswerDto {

    private final Long id;
    private final String definition;
    private final boolean isCorrect;

    public AnswerDto(Term term, boolean isCorrect) {
        this.id = term.getId();
        this.definition = term.getDefinition();
        this.isCorrect = isCorrect;
    }
}
