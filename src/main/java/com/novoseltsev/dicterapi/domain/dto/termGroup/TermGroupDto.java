package com.novoseltsev.dicterapi.domain.dto.termGroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dicterapi.domain.entity.TermGroup;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
