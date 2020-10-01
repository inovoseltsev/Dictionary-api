package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import com.novoseltsev.dictionaryapi.repository.TermGroupFolderRepository;
import com.novoseltsev.dictionaryapi.service.TermGroupFolderService;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import org.springframework.stereotype.Component;

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
    public TermGroupFolder createForUser(TermGroupFolder folder, Long userId) {
        return null;
    }

    @Override
    public TermGroupFolder update(TermGroupFolder folder) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public TermGroupFolder findById(Long id) {
        return null;
    }

    @Override
    public List<TermGroupFolder> findAllByUserId(Long userId) {
        return null;
    }
}
