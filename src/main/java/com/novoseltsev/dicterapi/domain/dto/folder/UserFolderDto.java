package com.novoseltsev.dicterapi.domain.dto.folder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dicterapi.domain.entity.Folder;
import com.novoseltsev.dicterapi.domain.entity.User;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFolderDto extends AbstractFolderDto {

    @Positive
    @NotNull
    private Long userId;

    @Override
    public Folder toEntity() {
        Folder folder = super.toEntity();
        folder.setUser(new User(userId));
        return folder;
    }
}
