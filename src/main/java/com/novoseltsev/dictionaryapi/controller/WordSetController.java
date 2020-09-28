package com.novoseltsev.dictionaryapi.controller;

import com.novoseltsev.dictionaryapi.domain.dto.WordSetDto;
import com.novoseltsev.dictionaryapi.domain.entity.WordSet;
import com.novoseltsev.dictionaryapi.service.WordSetService;
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
@RequestMapping("word-sets")
public class WordSetController {

    private final WordSetService wordSetService;

    @Autowired
    public WordSetController(WordSetService wordSetService) {
        this.wordSetService = wordSetService;
    }

    @GetMapping("/users/{userId}")
    public List<WordSetDto> findAllByUserId(@PathVariable Long userId) {
        return wordSetService.findAllByUserId(userId).stream()
                .map(WordSetDto::from).collect(Collectors.toList());
    }

    @PostMapping("users/{userId}")
    public ResponseEntity<WordSetDto> createForUser(
            @Valid @RequestBody WordSetDto wordSetDto,
            @PathVariable Long userId
    ) {
        WordSet createdWordSet =
                wordSetService.createForUser(wordSetDto.toWordSet(), userId);
        return new ResponseEntity<>(WordSetDto.from(createdWordSet),
                HttpStatus.CREATED);
    }

    @PutMapping
    public WordSetDto update(@Valid @RequestBody WordSetDto wordSetDto) {
        WordSet updatedWordSet = wordSetService.update(wordSetDto.toWordSet());
        return WordSetDto.from(updatedWordSet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        wordSetService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
