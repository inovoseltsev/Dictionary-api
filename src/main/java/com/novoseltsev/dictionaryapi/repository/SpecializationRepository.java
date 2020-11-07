package com.novoseltsev.dictionaryapi.repository;

import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepository extends CrudRepository<Specialization, Long> {

    List<Specialization> findAllByUserIdOrderByIdDesc(Long userId);
}
