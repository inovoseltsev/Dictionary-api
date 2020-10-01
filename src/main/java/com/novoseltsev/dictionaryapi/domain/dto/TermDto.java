package com.novoseltsev.dictionaryapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TermDto {

    @Positive
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public Term toWord() {
        return null;
    }

    public static TermDto from(Term term) {
        return null;
    }
}
