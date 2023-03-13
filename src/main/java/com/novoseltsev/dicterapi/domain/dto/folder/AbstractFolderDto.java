package com.novoseltsev.dicterapi.domain.dto.folder;

import com.novoseltsev.dicterapi.domain.dto.DtoMapper;
import com.novoseltsev.dicterapi.domain.entity.Folder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;


import static com.novoseltsev.dicterapi.validation.Pattern.DESCRIPTION_PATTERN;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.DESCRIPTION_ERROR;

@Getter
@NoArgsConstructor
public abstract class AbstractFolderDto implements DtoMapper<Folder> {

    @NotBlank
    private String name;

    @Pattern(regexp = DESCRIPTION_PATTERN, message = DESCRIPTION_ERROR)
    private String description;

    public AbstractFolderDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public Folder toEntity() {
        return new Folder(name, description);
    }
}
