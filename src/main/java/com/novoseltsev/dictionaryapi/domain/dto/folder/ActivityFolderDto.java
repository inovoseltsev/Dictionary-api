package com.novoseltsev.dictionaryapi.domain.dto.folder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Activity;
import com.novoseltsev.dictionaryapi.domain.entity.Folder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityFolderDto extends AbstractFolderDto {

    @Positive
    @NotNull
    private Long activityId;

    @Override
    public Folder toEntity() {
        Folder folder =  super.toEntity();
        folder.setActivity(new Activity(activityId));
        return folder;
    }
}
