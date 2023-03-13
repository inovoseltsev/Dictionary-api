package com.novoseltsev.dicterapi.service;

import com.novoseltsev.dicterapi.domain.entity.TermGroup;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TermGroupService {

    TermGroup createForUser(TermGroup termGroup);

    TermGroup createForFolder(TermGroup termGroup);

    TermGroup createForActivity(TermGroup termGroup);

    TermGroup update(TermGroup termGroup);

    void deleteById(Long id);

    TermGroup findById(Long id);

    List<TermGroup> findAllByUserId(Long userId);

    List<TermGroup> findAllByFolderId(Long folderId);

    List<TermGroup> findAllByActivityId(Long activityId);
}
