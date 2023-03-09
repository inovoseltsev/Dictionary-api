package com.novoseltsev.dicterapi.repository;

import com.novoseltsev.dicterapi.domain.entity.Folder;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends CrudRepository<Folder, Long> {

    List<Folder> findAllByUserIdOrderByIdDesc(Long userId);

    List<Folder> findAllByActivityIdOrderByIdDesc(Long activityId);
}
