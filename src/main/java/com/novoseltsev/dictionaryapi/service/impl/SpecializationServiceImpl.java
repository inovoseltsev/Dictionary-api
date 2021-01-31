package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.exception.ObjectNotFoundException;
import com.novoseltsev.dictionaryapi.repository.SpecializationRepository;
import com.novoseltsev.dictionaryapi.service.SpecializationService;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final UserService userService;
    private final MessageSourceAccessor messageAccessor;

    @Autowired
    public SpecializationServiceImpl(UserService userService, SpecializationRepository specializationRepository,
                                     MessageSourceAccessor messageAccessor) {
        this.userService = userService;
        this.specializationRepository = specializationRepository;
        this.messageAccessor = messageAccessor;
    }

    @Override
    public Specialization create(Specialization specialization) {
        Long userId = specialization.getUser().getId();
        User user = userService.findById(userId);
        user.addSpecialization(specialization);
        return specializationRepository.save(specialization);
    }

    @Override
    public Specialization update(Specialization specialization) {
        Specialization savedSpecialization = findById(specialization.getId());
        savedSpecialization.setName(specialization.getName());
        savedSpecialization.setDescription(specialization.getDescription());
        return specializationRepository.save(savedSpecialization);
    }

    @Override
    public void deleteById(Long id) {
        specializationRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Specialization findById(Long id) {
        String errorMessage = messageAccessor.getMessage("specialization.not.found");
        return specializationRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(errorMessage));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Specialization> findAllByUserId(Long userId) {
        return specializationRepository.findAllByUserIdOrderByIdDesc(userId);
    }
}
