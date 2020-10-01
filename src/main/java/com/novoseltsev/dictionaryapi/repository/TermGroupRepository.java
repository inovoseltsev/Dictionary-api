package com.novoseltsev.dictionaryapi.repository;

import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermGroupRepository extends CrudRepository<TermGroup, Long> {

    List<TermGroup> findAllByUserIdOrderById(Long userId);

    List<TermGroup> findAllByTermGroupFolderIdOrderById(Long folderId);
}
