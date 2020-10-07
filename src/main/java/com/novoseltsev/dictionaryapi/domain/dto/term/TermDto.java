package com.novoseltsev.dictionaryapi.domain.dto.term;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TermDto extends AbstractTermDto {

    @Positive
    private Long id;

    public TermDto(Long id, String name, String definition) {
        super(name, definition);
        this.id = id;
    }

    @Override
    public Term toEntity() {
        Term term = super.toEntity();
        term.setId(id);
        return term;
    }

    public static TermDto from(Term term) {
        return new TermDto(
                term.getId(),
                term.getName(),
                term.getDefinition()
        );
    }
}

