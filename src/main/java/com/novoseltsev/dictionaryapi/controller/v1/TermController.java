package com.novoseltsev.dictionaryapi.controller.v1;

import com.novoseltsev.dictionaryapi.domain.dto.term.AnswerDto;
import com.novoseltsev.dictionaryapi.domain.dto.term.GroupTermDto;
import com.novoseltsev.dictionaryapi.domain.dto.term.TermDto;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import com.novoseltsev.dictionaryapi.domain.status.TermAwareStatus;
import com.novoseltsev.dictionaryapi.service.TermService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("terms")
public class TermController {

    private final TermService termService;

    @Autowired
    public TermController(TermService termService) {
        this.termService = termService;
    }

    @GetMapping("/{id}")
    public TermDto findById(@PathVariable Long id) {
        return TermDto.from(termService.findById(id));
    }

    @GetMapping("/term-groups/{groupId}")
    public List<TermDto> findAllByTermGroupId(@PathVariable Long groupId) {
        return termService.findAllByTermGroupId(groupId).stream().map(TermDto::from).collect(Collectors.toList());
    }

    @GetMapping("/studying/answers/{termId}")
    public List<AnswerDto> getAnswersForTerm(@PathVariable Long termId) {
        return termService.getAnswersForTerm(termId);
    }

    @GetMapping("/studying/term-groups/{groupId}")
    public List<TermDto> getStudySet(@PathVariable Long groupId, @RequestParam(required = false) boolean shuffle) {
        List<Term> studySet = termService.getDefaultStudySet(groupId);
        if (shuffle) {
            Collections.shuffle(studySet);
        }
        return studySet.stream().map(TermDto::fromTermWithoutImages).collect(Collectors.toList());
    }

    @GetMapping("/studying/keywords/term-groups/{groupId}")
    public List<TermDto> getStudySetWithKeywords(@PathVariable Long groupId, @RequestParam(required = false) boolean shuffle) {
        List<Term> studySet = termService.getStudySetWithKeywords(groupId);
        if (shuffle) {
            Collections.shuffle(studySet);
        }
        return studySet.stream().map(TermDto::fromTermWithoutImages).collect(Collectors.toList());
    }

    @GetMapping("/studying/images/term-groups/{groupId}")
    public List<TermDto> getStudySetWithImages(@PathVariable Long groupId, @RequestParam(required = false) boolean shuffle) {
        List<Term> studySet = termService.getStudySetWithImages(groupId);
        if (shuffle) {
            Collections.shuffle(studySet);
        }
        return studySet.stream().map(TermDto::from).collect(Collectors.toList());
    }

    @GetMapping("/studying/chunks/term-groups/{groupId}")
    public List<List<TermDto>> getStudySetInChunks(@PathVariable Long groupId) {
        List<List<Term>> studyChunks = termService.getStudySetInChunks(groupId);
        List<List<TermDto>> studyChunksDto = new ArrayList<>();
        for (List<Term> chunk : studyChunks) {
            studyChunksDto.add(chunk.stream().map(TermDto::fromTermWithoutImages).collect(Collectors.toList()));
        }
        return studyChunksDto;
    }

    @PostMapping
    public ResponseEntity<TermDto> createForTermGroup(@RequestPart @Valid GroupTermDto groupTermDto,
                                                      @RequestPart(required = false) MultipartFile termImage) throws IOException {
        Term term = groupTermDto.toEntity();
        termService.uploadTermImage(termImage, term);
        TermDto termDto = TermDto.from(termService.createForTermGroup(term));
        return new ResponseEntity<>(termDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public TermDto update(@PathVariable Long id, @RequestPart @Valid TermDto termDto,
                          @RequestPart(required = false) MultipartFile termImage) throws IOException {
        termDto.setId(id);
        Term term = termDto.toEntity();
        termService.uploadTermImage(termImage, term);
        Term updatedTerm = termService.update(term);
        return TermDto.from(updatedTerm);
    }

    @PutMapping("/studying/aware-status/{id}")
    public void updateAwareStatus(@PathVariable Long id, @RequestParam TermAwareStatus awareStatus) {
        termService.updateAwareStatus(id, awareStatus);
    }

    @PutMapping("/studying/aware-status/reset/{groupId}")
    public void resetAwareStatusForTermGroup(@PathVariable Long groupId) {
        termService.resetAwareStatusForAllInTermGroup(groupId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        termService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
