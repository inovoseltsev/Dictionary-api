package com.novoseltsev.dictionaryapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class TermGroupDto {

    @Positive
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String description;

    public TermGroup toWordSet() {
        return new TermGroup(id, name, description);
    }

    public static TermGroupDto from(TermGroup termGroup) {
        return new TermGroupDto(
                termGroup.getId(),
                termGroup.getName(),
                termGroup.getDescription()
        );
    }
}
