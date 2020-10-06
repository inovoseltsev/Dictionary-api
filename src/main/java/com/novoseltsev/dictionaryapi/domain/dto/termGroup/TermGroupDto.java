package com.novoseltsev.dictionaryapi.domain.dto.termGroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TermGroupDto extends AbstractTermGroupDto {

    @Positive
    private Long id;

    public TermGroupDto(Long id, String name, String description) {
        super(name, description);
        this.id = id;
    }

    @Override
    public TermGroup toEntity() {
        TermGroup termGroup = super.toEntity();
        termGroup.setId(id);
        return termGroup;
    }

    public static TermGroupDto from(TermGroup termGroup) {
        return new TermGroupDto(
                termGroup.getId(),
                termGroup.getName(),
                termGroup.getDescription()
        );
    }
}
