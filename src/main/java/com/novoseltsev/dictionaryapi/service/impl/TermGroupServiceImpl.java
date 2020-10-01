package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils;
import com.novoseltsev.dictionaryapi.repository.TermGroupRepository;
import com.novoseltsev.dictionaryapi.service.TermGroupFolderService;
import com.novoseltsev.dictionaryapi.service.TermGroupService;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TermGroupServiceImpl implements TermGroupService {

    private final TermGroupRepository termGroupRepository;
    private final UserService userService;
    private final TermGroupFolderService termGroupFolderService;

    @Autowired
    public TermGroupServiceImpl(
            TermGroupRepository termGroupRepository, UserService userService,
            TermGroupFolderService termGroupFolderService
    ) {
        this.termGroupRepository = termGroupRepository;
        this.userService = userService;
        this.termGroupFolderService = termGroupFolderService;
    }

    @Override
    @Transactional
    public TermGroup createForUser(TermGroup termGroup, Long userId) {
        User user = userService.findById(userId);
        user.addTermGroup(termGroup);
        return termGroupRepository.save(termGroup);
    }

    @Override
    @Transactional
    public TermGroup createForTermGroupFolder(
            TermGroup termGroup, Long folderId) {
        TermGroupFolder folder = termGroupFolderService.findById(folderId);
        folder.addTermGroup(termGroup);
        return termGroupRepository.save(termGroup);
    }

    @Override
    @Transactional
    public TermGroup update(TermGroup termGroup) {
        TermGroup savedTermGroup = findById(termGroup.getId());
        savedTermGroup.setName(termGroup.getName());
        savedTermGroup.setDescription(termGroup.getDescription());
        return termGroupRepository.save(savedTermGroup);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        termGroupRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public TermGroup findById(Long id) {
        return termGroupRepository.findById(id)
                .orElseThrow(ExceptionUtils.OBJECT_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TermGroup> findAllByUserId(Long userId) {
        return termGroupRepository.findAllByUserIdOrderById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TermGroup> findAllByTermGroupFolderId(Long folderId) {
        return termGroupRepository.findAllByTermGroupFolderIdOrderById(folderId);
    }
}
