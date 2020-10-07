package com.novoseltsev.dictionaryapi.domain.dto.termGroupFolder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TermGroupFolderDto extends AbstractTermGroupFolderDto {

    @Positive
    private Long id;

    public TermGroupFolderDto(Long id, String name, String description) {
        super(name, description);
        this.id = id;
    }

    @Override
    public TermGroupFolder toEntity() {
        TermGroupFolder folder = super.toEntity();
        folder.setId(id);
        return folder;
    }

    public static TermGroupFolderDto from(TermGroupFolder folder) {
        return new TermGroupFolderDto(
                folder.getId(),
                folder.getName(),
                folder.getDescription()
        );
    }
}
