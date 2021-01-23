package com.novoseltsev.dictionaryapi.controller.v1;

import com.novoseltsev.dictionaryapi.domain.dto.specialization.SpecializationDto;
import com.novoseltsev.dictionaryapi.domain.dto.specialization.UserSpecializationDto;
import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import com.novoseltsev.dictionaryapi.service.SpecializationService;
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
@RequestMapping("specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    @Autowired
    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping("/{id}")
    public SpecializationDto findById(@PathVariable Long id) {
        return SpecializationDto.from(specializationService.findById(id));
    }

    @GetMapping("/users/{userId}")
    public List<SpecializationDto> findAllByUserId(@PathVariable Long userId) {
        return specializationService.findAllByUserIdDesc(userId).stream().map(SpecializationDto::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<SpecializationDto> create(@Valid @RequestBody UserSpecializationDto specializationDto) {
        Specialization createdSpecialization = specializationService.create(specializationDto.toEntity());
        return new ResponseEntity<>(SpecializationDto.from(createdSpecialization), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public SpecializationDto update(@PathVariable Long id, @Valid @RequestBody SpecializationDto specializationDto) {
        specializationDto.setId(id);
        Specialization updatedSpecialization = specializationService.update(specializationDto.toEntity());
        return SpecializationDto.from(updatedSpecialization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        specializationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
