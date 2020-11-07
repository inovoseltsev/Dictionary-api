package com.novoseltsev.dictionaryapi.repository;

import com.novoseltsev.dictionaryapi.domain.entity.TermGroupFolder;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermGroupFolderRepository extends CrudRepository<TermGroupFolder, Long> {

    List<TermGroupFolder> findAllByUserIdOrderByIdDesc(Long userId);

    List<TermGroupFolder> findAllBySpecializationIdOrderByIdDesc(Long specializationId);
}