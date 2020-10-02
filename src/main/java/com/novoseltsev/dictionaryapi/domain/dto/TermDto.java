package com.novoseltsev.dictionaryapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class TermDto {

    @Positive
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String definition;

    public Term toTerm() {
        return new Term(id, name, definition);
    }

    public static TermDto from(Term term) {
        return new TermDto(
                term.getId(),
                term.getName(),
                term.getDefinition()
        );
    }
}
