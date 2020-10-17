package com.novoseltsev.dictionaryapi.controller.v1;

import com.novoseltsev.dictionaryapi.domain.dto.term.GroupTermDto;
import com.novoseltsev.dictionaryapi.domain.dto.term.TermDto;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import com.novoseltsev.dictionaryapi.service.TermService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return termService.findAllByTermGroupId(groupId).stream().map(TermDto::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<TermDto> createForTermGroup(@Valid @RequestBody GroupTermDto termDto) {
        Term createdTerm = termService.createForTermGroup(termDto.toEntity());
        return new ResponseEntity<>(TermDto.from(createdTerm), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public TermDto update(@Valid @RequestBody TermDto termDto, @PathVariable Long id) {
        termDto.setId(id);
        Term updatedTerm = termService.update(termDto.toEntity());
        return TermDto.from(updatedTerm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        termService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
