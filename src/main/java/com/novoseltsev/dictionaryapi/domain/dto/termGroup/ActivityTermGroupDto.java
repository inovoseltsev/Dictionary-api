package com.novoseltsev.dictionaryapi.domain.dto.termGroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Activity;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties
public class ActivityTermGroupDto extends AbstractTermGroupDto {

    @Positive
    @NotNull
    private Long activityId;

    @Override
    public TermGroup toEntity() {
        TermGroup termGroup = super.toEntity();
        termGroup.setActivity(new Activity(activityId));
        return termGroup;
    }
}
