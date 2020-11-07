package com.novoseltsev.dictionaryapi.domain.dto.specialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecializationDto extends AbstractSpecializationDto {

    @Positive
    private Long id;

    public SpecializationDto(Long id, String name, String description) {
        super(name, description);
        this.id = id;
    }

    @Override
    public Specialization toEntity() {
        Specialization specialization = super.toEntity();
        specialization.setId(id);
        return specialization;
    }

    public static SpecializationDto from(Specialization specialization) {
        return new SpecializationDto(
                specialization.getId(),
                specialization.getName(),
                specialization.getDescription()
        );
    }
}
