package com.novoseltsev.dictionaryapi.controller.v1;

import com.novoseltsev.dictionaryapi.domain.dto.folder.ActivityFolderDto;
import com.novoseltsev.dictionaryapi.domain.dto.folder.FolderDto;
import com.novoseltsev.dictionaryapi.domain.dto.folder.UserFolderDto;
import com.novoseltsev.dictionaryapi.domain.entity.Folder;
import com.novoseltsev.dictionaryapi.service.FolderService;
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
@RequestMapping("folders")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping("/{id}")
    public FolderDto findById(@PathVariable Long id) {
        return FolderDto.from(folderService.findById(id));
    }

    @GetMapping("/users/{userId}")
    public List<FolderDto> findAllByUserId(@PathVariable Long userId) {
        return folderService.findAllByUserId(userId).stream().map(FolderDto::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/activities/{activityId}")
    public List<FolderDto> findAllByActivityId(@PathVariable Long activityId) {
        return folderService.findAllByActivityId(activityId).stream()
                .map(FolderDto::from).collect(Collectors.toList());
    }

    @PostMapping("/users")
    public ResponseEntity<FolderDto> createForUser(@Valid @RequestBody UserFolderDto folderDto) {
        Folder createdFolder = folderService.createForUser(folderDto.toEntity());
        return new ResponseEntity<>(FolderDto.from(createdFolder), HttpStatus.CREATED);
    }

    @PostMapping("/activities")
    public ResponseEntity<FolderDto> createForActivity(@Valid @RequestBody ActivityFolderDto folderDto) {
        Folder createdFolder = folderService.createForActivity(folderDto.toEntity());
        return new ResponseEntity<>(FolderDto.from(createdFolder), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public FolderDto update(@PathVariable Long id, @Valid @RequestBody FolderDto folderDto) {
        folderDto.setId(id);
        Folder updatedFolder = folderService.update(folderDto.toEntity());
        return FolderDto.from(updatedFolder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        folderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
