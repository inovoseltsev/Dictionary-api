package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils;
import com.novoseltsev.dictionaryapi.repository.SpecializationRepository;
import com.novoseltsev.dictionaryapi.service.SpecializationService;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final UserService userService;

    @Autowired
    public SpecializationServiceImpl(SpecializationRepository specializationRepository, UserService userService) {
        this.specializationRepository = specializationRepository;
        this.userService = userService;
    }


    @Override
    @Transactional
    public Specialization create(Specialization specialization) {
        Long userId = specialization.getUser().getId();
        User user = userService.findById(userId);
        user.addSpecialization(specialization);
        return specializationRepository.save(specialization);
    }

    @Override
    @Transactional
    public Specialization update(Specialization specialization) {
        Specialization savedSpecialization = findById(specialization.getId());
        savedSpecialization.setName(specialization.getName());
        savedSpecialization.setDescription(specialization.getDescription());
        return specializationRepository.save(savedSpecialization);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        specializationRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Specialization findById(Long id) {
        return specializationRepository.findById(id).orElseThrow(ExceptionUtils.OBJECT_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Specialization> findAllByUserIdDesc(Long userId) {
        return specializationRepository.findAllByUserIdOrderByIdDesc(userId);
    }
}
