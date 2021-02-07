package com.novoseltsev.dictionaryapi.domain.dto.folder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Folder;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderDto extends AbstractFolderDto {

    @Positive
    private Long id;

    public FolderDto(Long id, String name, String description) {
        super(name, description);
        this.id = id;
    }

    @Override
    public Folder toEntity() {
        Folder folder = super.toEntity();
        folder.setId(id);
        return folder;
    }

    public static FolderDto from(Folder folder) {
        return new FolderDto(
                folder.getId(),
                folder.getName(),
                folder.getDescription()
        );
    }
}
