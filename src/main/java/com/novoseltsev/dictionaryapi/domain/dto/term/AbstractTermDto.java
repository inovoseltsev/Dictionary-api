package com.novoseltsev.dictionaryapi.domain.dto.term;

import com.novoseltsev.dictionaryapi.domain.dto.DtoMapper;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;


import static com.novoseltsev.dictionaryapi.validation.Pattern.KEY_WORD_PATTERN;

@Getter
@NoArgsConstructor
public abstract class AbstractTermDto implements DtoMapper<Term> {

    @NotBlank
    private String name;

    @NotBlank
    private String definition;

    @Pattern(regexp = KEY_WORD_PATTERN)
    private String keyword;

    //TODO Add aware status

    public AbstractTermDto(String name, String definition, String keyword) {
        this.name = name;
        this.definition = definition;
        this.keyword = keyword;
    }

    @Override
    public Term toEntity() {
        return new Term(name, definition, keyword);
    }
}
