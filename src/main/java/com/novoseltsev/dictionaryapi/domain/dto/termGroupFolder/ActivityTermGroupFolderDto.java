package com.novoseltsev.dictionaryapi.domain.dto.termGroupFolder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Activity;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityTermGroupFolderDto extends AbstractTermGroupFolderDto {

    @Positive
    @NotNull
    private Long activityId;

    @Override
    public TermGroupFolder toEntity() {
        TermGroupFolder folder =  super.toEntity();
        folder.setActivity(new Activity(activityId));
        return folder;
    }
}
