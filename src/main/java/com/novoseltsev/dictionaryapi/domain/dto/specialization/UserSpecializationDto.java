package com.novoseltsev.dictionaryapi.domain.dto.specialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSpecializationDto extends AbstractSpecializationDto {

    @Positive
    @NotNull
    private Long userId;

    @Override
    public Specialization toEntity() {
        Specialization specialization = super.toEntity();
        specialization.setUser(new User(userId));
        return specialization;
    }
}
