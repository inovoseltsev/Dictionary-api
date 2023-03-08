package com.novoseltsev.dictionaryapi.domain.model.study;

import com.novoseltsev.dictionaryapi.domain.entity.Term;
import lombok.Data;

import java.util.List;

@Data
public class StudyTerm {

    private final Term term;

    private final List<Answer> answers;
}
