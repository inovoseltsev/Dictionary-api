package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils;
import com.novoseltsev.dictionaryapi.repository.TermGroupRepository;
import com.novoseltsev.dictionaryapi.service.SpecializationService;
import com.novoseltsev.dictionaryapi.service.TermGroupFolderService;
import com.novoseltsev.dictionaryapi.service.TermGroupService;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TermGroupServiceImpl implements TermGroupService {

    private final TermGroupRepository termGroupRepository;
    private final TermGroupFolderService termGroupFolderService;
    private final SpecializationService specializationService;
    private final UserService userService;

    @Autowired
    public TermGroupServiceImpl(TermGroupRepository termGroupRepository, TermGroupFolderService termGroupFolderService,
                                SpecializationService specializationService, UserService userService) {
        this.termGroupRepository = termGroupRepository;
        this.userService = userService;
        this.termGroupFolderService = termGroupFolderService;
        this.specializationService = specializationService;
    }

    @Override
    public TermGroup createForUser(TermGroup termGroup) {
        Long userId = termGroup.getUser().getId();
        User user = userService.findById(userId);
        user.addTermGroup(termGroup);
        return termGroupRepository.save(termGroup);
    }

    @Override
    public TermGroup createForTermGroupFolder(TermGroup termGroup) {
        Long folderId = termGroup.getTermGroupFolder().getId();
        TermGroupFolder folder = termGroupFolderService.findById(folderId);
        folder.addTermGroup(termGroup);
        return termGroupRepository.save(termGroup);
    }

    @Override
    public TermGroup createForSpecialization(TermGroup termGroup) {
        Long specializationId = termGroup.getSpecialization().getId();
        Specialization specialization = specializationService.findById(specializationId);
        specialization.addTermGroup(termGroup);
        return termGroupRepository.save(termGroup);
    }

    @Override
    public TermGroup update(TermGroup termGroup) {
        TermGroup savedTermGroup = findById(termGroup.getId());
        savedTermGroup.setName(termGroup.getName());
        savedTermGroup.setDescription(termGroup.getDescription());
        return termGroupRepository.save(savedTermGroup);
    }

    @Override
    public void deleteById(Long id) {
        termGroupRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public TermGroup findById(Long id) {
        return termGroupRepository.findById(id).orElseThrow(ExceptionUtils.OBJECT_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<TermGroup> findAllByUserIdDesc(Long userId) {
        return termGroupRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<TermGroup> findAllByTermGroupFolderIdDesc(Long folderId) {
        return termGroupRepository.findAllByTermGroupFolderIdOrderByIdDesc(folderId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<TermGroup> findAllBySpecializationIdDesc(Long specializationId) {
        return termGroupRepository.findAllBySpecializationIdOrderByIdDesc(specializationId);
    }
}
