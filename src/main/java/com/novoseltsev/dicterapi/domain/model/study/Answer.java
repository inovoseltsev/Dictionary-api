package com.novoseltsev.dicterapi.domain.model.study;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Answer {

    private final Long id;

    private final String definition;

    private final boolean isCorrect;
}
