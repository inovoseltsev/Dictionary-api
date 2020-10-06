package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.entity.Term;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TermService {

    Term createForTermGroup(Term term);

    Term update(Term term);

    void deleteById(Long id);

    Term findById(Long id);

    List<Term> findAllByTermGroupId(Long termGroupId);
}
