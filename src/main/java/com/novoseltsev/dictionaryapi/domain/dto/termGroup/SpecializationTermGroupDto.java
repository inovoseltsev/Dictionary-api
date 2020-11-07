package com.novoseltsev.dictionaryapi.domain.dto.termGroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties
public class SpecializationTermGroupDto extends AbstractTermGroupDto {

    @Positive
    @NotNull
    private Long specializationId;

    @Override
    public TermGroup toEntity() {
        TermGroup termGroup = super.toEntity();
        termGroup.setSpecialization(new Specialization(specializationId));
        return termGroup;
    }
}
