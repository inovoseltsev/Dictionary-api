package com.novoseltsev.dictionaryapi.domain.dto.termGroupFolder;

import com.novoseltsev.dictionaryapi.domain.dto.DtoMapper;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;


import static com.novoseltsev.dictionaryapi.validation.Pattern.DESCRIPTION_PATTERN;
import static com.novoseltsev.dictionaryapi.validation.ValidationMessage.DESCRIPTION_ERROR;

@Getter
@NoArgsConstructor
public abstract class AbstractTermGroupFolderDto implements DtoMapper<TermGroupFolder> {

    @NotBlank
    private String name;

    @Pattern(regexp = DESCRIPTION_PATTERN, message = DESCRIPTION_ERROR)
    private String description;

    public AbstractTermGroupFolderDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public TermGroupFolder toEntity() {
        return new TermGroupFolder(name, description);
    }
}
