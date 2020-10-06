package com.novoseltsev.dictionaryapi.domain.dto.termGroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import javax.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TermGroupCreationDto extends AbstractTermGroupDto {

    @Positive
    private Long userId;

    @Positive
    private Long termGroupFolderId;

    @Override
    public TermGroup toEntity() {
        TermGroup termGroup = super.toEntity();
        termGroup.setUser(new User(userId));
        termGroup.setTermGroupFolder(new TermGroupFolder(termGroupFolderId));
        return termGroup;
    }
}
