package com.novoseltsev.dicterapi.repository;

import com.novoseltsev.dicterapi.domain.entity.Term;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermRepository extends CrudRepository<Term, Long> {

    List<Term> findAllByTermGroupIdOrderByIdDesc(Long wordSetId);
}
