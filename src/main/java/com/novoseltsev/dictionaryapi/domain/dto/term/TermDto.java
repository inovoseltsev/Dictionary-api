package com.novoseltsev.dictionaryapi.domain.dto.term;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TermDto extends AbstractTermDto {

    @Positive
    private Long id;

    private byte[] image;

    public TermDto(Long id, String name, String definition, String keyword, byte[] image) {
        super(name, definition, keyword);
        this.id = id;
        this.image = image;
    }

    public TermDto(Long id, String name, String definition, String keyword) {
        super(name, definition, keyword);
        this.id = id;
    }

    @Override
    public Term toEntity() {
        Term term = super.toEntity();
        term.setId(id);
        return term;
    }

    public static TermDto from(Term term) {
        byte[] image = new byte[]{};
        if (!StringUtils.isEmpty(term.getImagePath())) {
            try {
                image = Files.readAllBytes(new File(term.getImagePath()).toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new TermDto(
                term.getId(),
                term.getName(),
                term.getDefinition(),
                term.getKeyword(),
                image
        );
    }

    public static TermDto fromTermWithoutImages(Term term) {
        return new TermDto(
                term.getId(),
                term.getName(),
                term.getDefinition(),
                term.getKeyword()
        );
    }
}

