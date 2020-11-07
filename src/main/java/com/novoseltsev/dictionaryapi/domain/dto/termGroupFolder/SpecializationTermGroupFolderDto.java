package com.novoseltsev.dictionaryapi.domain.dto.termGroupFolder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecializationTermGroupFolderDto extends AbstractTermGroupFolderDto {

    @Positive
    @NotNull
    private Long specializationId;

    @Override
    public TermGroupFolder toEntity() {
        TermGroupFolder folder =  super.toEntity();
        folder.setSpecialization(new Specialization(specializationId));
        return folder;
    }
}
