package com.novoseltsev.dictionaryapi.domain.dto.term;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AnswerDto {

    private final Long id;
    private final String definition;

    @Getter(AccessLevel.NONE)
    private final boolean isCorrect;

    @JsonProperty("isCorrect")
    public boolean isCorrect() {
        return isCorrect;
    }
}
