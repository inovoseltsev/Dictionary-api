package com.novoseltsev.dicterapi.repository;

import com.novoseltsev.dicterapi.domain.entity.TermGroup;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermGroupRepository extends CrudRepository<TermGroup, Long> {

    List<TermGroup> findAllByUserIdOrderByIdDesc(Long userId);

    List<TermGroup> findAllByFolderIdOrderByIdDesc(Long folderId);

    List<TermGroup> findAllByActivityIdOrderByIdDesc(Long activityId);
}
