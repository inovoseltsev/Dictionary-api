package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils;
import com.novoseltsev.dictionaryapi.repository.TermGroupFolderRepository;
import com.novoseltsev.dictionaryapi.service.TermGroupFolderService;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TermGroupFolderServiceImpl implements TermGroupFolderService {

    private final TermGroupFolderRepository termGroupFolderRepository;
    private final UserService userService;

    public TermGroupFolderServiceImpl(
            TermGroupFolderRepository termGroupFolderRepository,
            UserService userService
    ) {
        this.termGroupFolderRepository = termGroupFolderRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public TermGroupFolder createForUser(TermGroupFolder folder) {
        Long userId = folder.getUser().getId();
        User user = userService.findById(userId);
        user.addTermGroupFolder(folder);
        return termGroupFolderRepository.save(folder);
    }

    @Override
    @Transactional
    public TermGroupFolder update(TermGroupFolder folder) {
        TermGroupFolder savedFolder = findById(folder.getId());
        savedFolder.setName(folder.getName());
        savedFolder.setDescription(folder.getDescription());
        return termGroupFolderRepository.save(savedFolder);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        termGroupFolderRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public TermGroupFolder findById(Long id) {
        return termGroupFolderRepository.findById(id).orElseThrow(ExceptionUtils.OBJECT_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TermGroupFolder> findAllByUserId(Long userId) {
        return termGroupFolderRepository.findAllByUserIdOrderById(userId);
    }
}
