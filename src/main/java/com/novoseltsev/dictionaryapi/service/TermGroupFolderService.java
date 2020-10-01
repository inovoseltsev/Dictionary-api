package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TermGroupFolderService {

    TermGroupFolder createForUser(TermGroupFolder folder, Long userId);

    TermGroupFolder update(TermGroupFolder folder);

    void deleteById(Long id);

    TermGroupFolder findById(Long id);

    List<TermGroupFolder> findAllByUserId(Long userId);

}
