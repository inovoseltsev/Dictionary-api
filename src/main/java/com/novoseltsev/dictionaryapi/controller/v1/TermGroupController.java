package com.novoseltsev.dictionaryapi.controller.v1;

import com.novoseltsev.dictionaryapi.domain.dto.termGroup.ActivityTermGroupDto;
import com.novoseltsev.dictionaryapi.domain.dto.termGroup.FolderTermGroupDto;
import com.novoseltsev.dictionaryapi.domain.dto.termGroup.TermGroupDto;
import com.novoseltsev.dictionaryapi.domain.dto.termGroup.UserTermGroupDto;
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
        return termGroupService.findAllByUserId(userId).stream().map(TermGroupDto::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/term-group-folders/{folderId}")
    public List<TermGroupDto> findAllByFolderId(@PathVariable Long folderId) {
        return termGroupService.findAllByFolderId(folderId).stream().map(TermGroupDto::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/activities/{activityId}")
    public List<TermGroupDto> findAllByActivityId(@PathVariable Long activityId) {
        return termGroupService.findAllByActivityId(activityId).stream().map(TermGroupDto::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/users")
    public ResponseEntity<TermGroupDto> createForUser(
            @Valid @RequestBody UserTermGroupDto termGroupDto) {
        TermGroup createdTermGroup = termGroupService.createForUser(termGroupDto.toEntity());
        return new ResponseEntity<>(TermGroupDto.from(createdTermGroup), HttpStatus.CREATED);
    }

    @PostMapping("/term-group-folders")
    public ResponseEntity<TermGroupDto> createForFolder(
            @Valid @RequestBody FolderTermGroupDto termGroupDto) {
        TermGroup createdTermGroup = termGroupService.createForFolder(termGroupDto.toEntity());
        return new ResponseEntity<>(TermGroupDto.from(createdTermGroup), HttpStatus.CREATED);
    }

    @PostMapping("/activities")
    public ResponseEntity<TermGroupDto> createForActivity(
            @Valid @RequestBody ActivityTermGroupDto termGroupDto) {
        TermGroup createdTermGroup = termGroupService.createForActivity(termGroupDto.toEntity());
        return new ResponseEntity<>(TermGroupDto.from(createdTermGroup), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public TermGroupDto update(
            @PathVariable Long id, @Valid @RequestBody TermGroupDto termGroupDto) {
        termGroupDto.setId(id);
        TermGroup updatedTermGroup = termGroupService.update(termGroupDto.toEntity());
        return TermGroupDto.from(updatedTermGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        termGroupService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
