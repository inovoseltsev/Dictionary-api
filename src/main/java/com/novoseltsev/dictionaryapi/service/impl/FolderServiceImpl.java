package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.Activity;
import com.novoseltsev.dictionaryapi.domain.entity.Folder;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.exception.ObjectNotFoundException;
import com.novoseltsev.dictionaryapi.repository.FolderRepository;
import com.novoseltsev.dictionaryapi.service.ActivityService;
import com.novoseltsev.dictionaryapi.service.FolderService;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final UserService userService;
    private final ActivityService activityService;
    private final MessageSourceAccessor messageAccessor;


    public FolderServiceImpl(FolderRepository folderRepository, UserService userService,
                             ActivityService activityService, MessageSourceAccessor messageAccessor) {
        this.folderRepository = folderRepository;
        this.userService = userService;
        this.activityService = activityService;
        this.messageAccessor = messageAccessor;
    }

    @Override
    public Folder createForUser(Folder folder) {
        Long userId = folder.getUser().getId();
        User user = userService.findById(userId);
        user.addFolder(folder);
        return folderRepository.save(folder);
    }

    @Override
    public Folder createForActivity(Folder folder) {
        Long activityId = folder.getActivity().getId();
        Activity activity = activityService.findById(activityId);
        activity.addFolder(folder);
        return folderRepository.save(folder);
    }

    @Override
    public Folder update(Folder folder) {
        Folder savedFolder = findById(folder.getId());
        savedFolder.setName(folder.getName());
        savedFolder.setDescription(folder.getDescription());
        return folderRepository.save(savedFolder);
    }

    @Override
    public void deleteById(Long id) {
        folderRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Folder findById(Long id) {
        String errorMessage = messageAccessor.getMessage("term.group.folder.not.found");
        return folderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(errorMessage));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Folder> findAllByUserId(Long userId) {
        return folderRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Folder> findAllByActivityId(Long activityId) {
        return folderRepository.findAllByActivityIdOrderByIdDesc(activityId);
    }
}
