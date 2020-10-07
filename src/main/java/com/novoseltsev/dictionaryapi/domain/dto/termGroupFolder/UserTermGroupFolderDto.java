package com.novoseltsev.dictionaryapi.domain.dto.termGroupFolder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTermGroupFolderDto extends AbstractTermGroupFolderDto {

    @Positive
    @NotNull
    private Long userId;

    @Override
    public TermGroupFolder toEntity() {
        TermGroupFolder folder = super.toEntity();
        folder.setUser(new User(userId));
        return folder;
    }
}
