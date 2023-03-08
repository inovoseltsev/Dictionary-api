package com.novoseltsev.dictionaryapi.domain.dto.term;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import com.novoseltsev.dictionaryapi.domain.model.study.StudyTerm;
import com.novoseltsev.dictionaryapi.domain.status.TermAwareStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TermDto extends AbstractTermDto {

    @Positive
    private Long id;

    TermAwareStatus awareStatus;

    private Map<String, Object> imageFile;

    private List<AnswerDto> answers;

    public TermDto(Long id, String name, String definition, String keyword, TermAwareStatus awareStatus,
                   Map<String, Object> imageFile) {
        super(name, definition, keyword);
        this.id = id;
        this.awareStatus = awareStatus;
        this.imageFile = imageFile;
    }

    public TermDto(Long id, String name, String definition, String keyword, TermAwareStatus awareStatus) {
        super(name, definition, keyword);
        this.id = id;
        this.awareStatus = awareStatus;
    }

    @Override
    public Term toEntity() {
        Term term = super.toEntity();
        term.setId(id);
        return term;
    }

    public static TermDto toTermDto(Term term) {
        return new TermDto(
                term.getId(),
                term.getName(),
                term.getDefinition(),
                term.getKeyword(),
                term.getAwareStatus()
        );
    }

    public static TermDto toTermDto(StudyTerm studyTerm) {
        var termDto = toTermDto(studyTerm.getTerm());
        var answerDtos = studyTerm.getAnswers().stream()
                .map(it -> new AnswerDto(it.getId(), it.getDefinition(), it.isCorrect()))
                .collect(Collectors.toList());
        termDto.setAnswers(answerDtos);
        return termDto;
    }

    public static TermDto toTermDtoWithImage(Term term) {
        Map<String, Object> imageFile = new HashMap<>();
        byte[] imageContent = new byte[]{};
        String imagePath = term.getImagePath();
        String imageName = "";
        if (imagePath != null && !imagePath.isBlank()) {
            imageName = imagePath.substring(imagePath.indexOf(".") + 1);
            try {
                imageContent = Files.readAllBytes(new File(term.getImagePath()).toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        imageFile.put("name", imageName);
        imageFile.put("content", imageContent);
        return new TermDto(
                term.getId(),
                term.getName(),
                term.getDefinition(),
                term.getKeyword(),
                term.getAwareStatus(),
                imageFile
        );
    }

    public static TermDto toTermDtoWithImage(StudyTerm studyTerm) {
        var termDto = toTermDtoWithImage(studyTerm.getTerm());
        var answerDtos = studyTerm.getAnswers().stream()
                .map(it -> new AnswerDto(it.getId(), it.getDefinition(), it.isCorrect()))
                .collect(Collectors.toList());
        termDto.setAnswers(answerDtos);
        return termDto;
    }
}

