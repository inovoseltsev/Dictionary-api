package com.novoseltsev.dictionaryapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.WordSet;
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
public class WordSetDto {

    @Positive
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String description;

    public WordSet toWordSet() {
        return new WordSet(id, name, description);
    }

    public static WordSetDto from(WordSet wordSet) {
        return new WordSetDto(
                wordSet.getId(),
                wordSet.getName(),
                wordSet.getDescription()
        );
    }
}
