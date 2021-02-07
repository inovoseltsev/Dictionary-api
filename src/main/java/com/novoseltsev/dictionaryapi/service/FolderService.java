package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.entity.Folder;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface FolderService {

    Folder createForUser(Folder folder);

    Folder createForActivity(Folder folder);

    Folder update(Folder folder);

    void deleteById(Long id);

    Folder findById(Long id);

    List<Folder> findAllByUserId(Long userId);

    List<Folder> findAllByActivityId(Long activityId);
}
