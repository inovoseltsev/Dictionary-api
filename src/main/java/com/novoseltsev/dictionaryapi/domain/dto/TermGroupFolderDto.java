package com.novoseltsev.dictionaryapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.DESCRIPTION_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.DESCRIPTION_PATTERN;

@Data
@JsonIgnoreProperties
@NoArgsConstructor
@AllArgsConstructor
public class TermGroupFolderDto {

    @Positive
    private Long id;

    @NotBlank
    private String name;

    @Pattern(regexp = DESCRIPTION_PATTERN, message = DESCRIPTION_ERROR)
    private String description;

    public TermGroupFolder toTermGroupFolder() {
        return new TermGroupFolder(id, name, description);
    }

    public static TermGroupFolderDto from(TermGroupFolder folder) {
        return new TermGroupFolderDto(
                folder.getId(),
                folder.getName(),
                folder.getDescription()
        );
    }
}
