package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.exception.ObjectNotFoundException;
import com.novoseltsev.dictionaryapi.repository.TermGroupFolderRepository;
import com.novoseltsev.dictionaryapi.service.SpecializationService;
import com.novoseltsev.dictionaryapi.service.TermGroupFolderService;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TermGroupFolderServiceImpl implements TermGroupFolderService {

    private final TermGroupFolderRepository termGroupFolderRepository;
    private final UserService userService;
    private final SpecializationService specializationService;
    private final MessageSourceAccessor messageAccessor;


    public TermGroupFolderServiceImpl(TermGroupFolderRepository termGroupFolderRepository, UserService userService,
                                      SpecializationService specializationService, MessageSourceAccessor messageAccessor) {
        this.termGroupFolderRepository = termGroupFolderRepository;
        this.userService = userService;
        this.specializationService = specializationService;
        this.messageAccessor = messageAccessor;
    }

    @Override
    public TermGroupFolder createForUser(TermGroupFolder folder) {
        Long userId = folder.getUser().getId();
        User user = userService.findById(userId);
        user.addTermGroupFolder(folder);
        return termGroupFolderRepository.save(folder);
    }

    @Override
    public TermGroupFolder createForSpecialization(TermGroupFolder folder) {
        Long specializationId = folder.getSpecialization().getId();
        Specialization specialization = specializationService.findById(specializationId);
        specialization.addTermGroupFolder(folder);
        return termGroupFolderRepository.save(folder);
    }

    @Override
    public TermGroupFolder update(TermGroupFolder folder) {
        TermGroupFolder savedFolder = findById(folder.getId());
        savedFolder.setName(folder.getName());
        savedFolder.setDescription(folder.getDescription());
        return termGroupFolderRepository.save(savedFolder);
    }

    @Override
    public void deleteById(Long id) {
        termGroupFolderRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public TermGroupFolder findById(Long id) {
        String errorMessage = messageAccessor.getMessage("term.group.folder.not.found");
        return termGroupFolderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(errorMessage));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<TermGroupFolder> findAllByUserId(Long userId) {
        return termGroupFolderRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<TermGroupFolder> findAllBySpecializationId(Long specializationId) {
        return termGroupFolderRepository.findAllBySpecializationIdOrderByIdDesc(specializationId);
    }
}
