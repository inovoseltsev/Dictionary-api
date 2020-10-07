package com.novoseltsev.dictionaryapi.domain.dto.termGroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderTermGroupDto extends AbstractTermGroupDto {

    @NotNull
    @Positive
    private Long termGroupFolderId;

    @Override
    public TermGroup toEntity() {
        TermGroup termGroup = super.toEntity();
        termGroup.setTermGroupFolder(new TermGroupFolder(termGroupFolderId));
        return termGroup;
    }
}
