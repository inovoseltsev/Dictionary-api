package com.novoseltsev.dicterapi.service.impl;

import com.novoseltsev.dicterapi.domain.entity.Activity;
import com.novoseltsev.dicterapi.domain.entity.Folder;
import com.novoseltsev.dicterapi.domain.entity.TermGroup;
import com.novoseltsev.dicterapi.domain.entity.User;
import com.novoseltsev.dicterapi.exception.ObjectNotFoundException;
import com.novoseltsev.dicterapi.repository.TermGroupRepository;
import com.novoseltsev.dicterapi.service.ActivityService;
import com.novoseltsev.dicterapi.service.FolderService;
import com.novoseltsev.dicterapi.service.TermGroupService;
import com.novoseltsev.dicterapi.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class TermGroupServiceImpl implements TermGroupService {

    private final TermGroupRepository termGroupRepository;
    private final FolderService folderService;
    private final ActivityService activityService;
    private final UserService userService;

    @Override
    public TermGroup createForUser(TermGroup termGroup) {
        Long userId = termGroup.getUser().getId();
        User user = userService.findById(userId);
        user.addTermGroup(termGroup);
        return termGroupRepository.save(termGroup);
    }

    @Override
    public TermGroup createForFolder(TermGroup termGroup) {
        Long folderId = termGroup.getFolder().getId();
        Folder folder = folderService.findById(folderId);
        folder.addTermGroup(termGroup);
        return termGroupRepository.save(termGroup);
    }

    @Override
    public TermGroup createForActivity(TermGroup termGroup) {
        Long activityId = termGroup.getActivity().getId();
        Activity activity = activityService.findById(activityId);
        activity.addTermGroup(termGroup);
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
        return termGroupRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Term group not found"));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<TermGroup> findAllByUserId(Long userId) {
        return termGroupRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<TermGroup> findAllByFolderId(Long folderId) {
        return termGroupRepository.findAllByFolderIdOrderByIdDesc(folderId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<TermGroup> findAllByActivityId(Long activityId) {
        return termGroupRepository.findAllByActivityIdOrderByIdDesc(activityId);
    }
}
