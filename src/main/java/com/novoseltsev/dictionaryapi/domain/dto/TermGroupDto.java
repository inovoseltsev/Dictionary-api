package com.novoseltsev.dictionaryapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.DESCRIPTION_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.DESCRIPTION_PATTERN;

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

    @Pattern(regexp = DESCRIPTION_PATTERN, message = DESCRIPTION_ERROR)
    private String description;

    public TermGroup toTermGroup() {
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
