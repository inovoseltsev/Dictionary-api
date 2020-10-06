package com.novoseltsev.dictionaryapi.controller.v1;

import com.novoseltsev.dictionaryapi.domain.dto.termGroup.TermGroupCreationDto;
import com.novoseltsev.dictionaryapi.domain.dto.termGroup.TermGroupDto;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import com.novoseltsev.dictionaryapi.service.TermGroupService;
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
@RequestMapping("term-groups")
public class TermGroupController {

    private final TermGroupService termGroupService;

    @Autowired
    public TermGroupController(TermGroupService termGroupService) {
        this.termGroupService = termGroupService;
    }

    @GetMapping("/{id}")
    public TermGroupDto findById(@PathVariable Long id) {
        return TermGroupDto.from(termGroupService.findById(id));
    }

    @GetMapping("/users/{userId}")
    public List<TermGroupDto> findAllByUserId(@PathVariable Long userId) {
        return termGroupService.findAllByUserId(userId).stream()
                .map(TermGroupDto::from).collect(Collectors.toList());
    }

    @GetMapping("/term-group-folders/{folderId}")
    public List<TermGroupDto> findAllByTermGroupFolderId(
            @PathVariable Long folderId) {
        return termGroupService.findAllByTermGroupFolderId(folderId).stream()
                .map(TermGroupDto::from).collect(Collectors.toList());
    }

    @PostMapping("/users")
    public ResponseEntity<TermGroupDto> createForUser(
            @Valid @RequestBody TermGroupCreationDto termGroupDto) {
        TermGroup createdTermGroup = termGroupService
                .createForUser(termGroupDto.toEntity());
        return new ResponseEntity<>(TermGroupDto.from(createdTermGroup),
                HttpStatus.CREATED);
    }

    @PostMapping("/term-group-folders")
    public ResponseEntity<TermGroupDto> createForTermGroupFolder(
            @Valid @RequestBody TermGroupCreationDto termGroupDto) {
        TermGroup createdTermGroup = termGroupService
                .createForTermGroupFolder(termGroupDto.toEntity());
        return new ResponseEntity<>(TermGroupDto.from(createdTermGroup),
                HttpStatus.CREATED);
    }

    @PutMapping
    public TermGroupDto update(@Valid @RequestBody TermGroupDto termGroupDto) {
        TermGroup updatedTermGroup = termGroupService
                .update(termGroupDto.toEntity());
        return TermGroupDto.from(updatedTermGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        termGroupService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
