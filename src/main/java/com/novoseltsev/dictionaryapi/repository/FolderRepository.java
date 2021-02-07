package com.novoseltsev.dictionaryapi.repository;

import com.novoseltsev.dictionaryapi.domain.entity.Folder;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends CrudRepository<Folder, Long> {

    List<Folder> findAllByUserIdOrderByIdDesc(Long userId);

    List<Folder> findAllByActivityIdOrderByIdDesc(Long activityId);
}
