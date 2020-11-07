package com.novoseltsev.dictionaryapi.controller.v1;

import com.novoseltsev.dictionaryapi.domain.dto.termGroupFolder.SpecializationTermGroupFolderDto;
import com.novoseltsev.dictionaryapi.domain.dto.termGroupFolder.TermGroupFolderDto;
import com.novoseltsev.dictionaryapi.domain.dto.termGroupFolder.UserTermGroupFolderDto;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import com.novoseltsev.dictionaryapi.service.TermGroupFolderService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
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
@RequestMapping("term-group-folders")
public class TermGroupFolderController {

    private final TermGroupFolderService termGroupFolderService;

    public TermGroupFolderController(TermGroupFolderService termGroupFolderService) {
        this.termGroupFolderService = termGroupFolderService;
    }

    @GetMapping("/{id}")
    public TermGroupFolderDto findById(@PathVariable Long id) {
        return TermGroupFolderDto.from(termGroupFolderService.findById(id));
    }

    @GetMapping("/users/{userId}")
    public List<TermGroupFolderDto> findAllByUserId(@PathVariable Long userId) {
        return termGroupFolderService.findAllByUserIdDesc(userId).stream()
                .map(TermGroupFolderDto::from).collect(Collectors.toList());
    }

    @GetMapping("/specializations/{specializationId}")
    public List<TermGroupFolderDto> findAllBySpecializationId(@PathVariable Long specializationId) {
        return termGroupFolderService.findAllBySpecializationIdDesc(specializationId).stream()
                .map(TermGroupFolderDto::from).collect(Collectors.toList());
    }

    @PostMapping("/users")
    public ResponseEntity<TermGroupFolderDto> createForUser(
            @Valid @RequestBody UserTermGroupFolderDto folderDto) {
        TermGroupFolder createdFolder = termGroupFolderService.createForUser(folderDto.toEntity());
        return new ResponseEntity<>(TermGroupFolderDto.from(createdFolder), HttpStatus.CREATED);
    }

    @PostMapping("/specializations")
    public ResponseEntity<TermGroupFolderDto> createForSpecialization(
            @Valid @RequestBody SpecializationTermGroupFolderDto folderDto) {
        TermGroupFolder createdFolder = termGroupFolderService.createForSpecialization(folderDto.toEntity());
        return new ResponseEntity<>(TermGroupFolderDto.from(createdFolder), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public TermGroupFolderDto update(
            @PathVariable Long id, @Valid @RequestBody TermGroupFolderDto folderDto) {
        folderDto.setId(id);
        TermGroupFolder updatedFolder = termGroupFolderService.update(folderDto.toEntity());
        return TermGroupFolderDto.from(updatedFolder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        termGroupFolderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
