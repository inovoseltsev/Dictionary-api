package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.Term;
import com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils;
import com.novoseltsev.dictionaryapi.repository.TermRepository;
import com.novoseltsev.dictionaryapi.service.TermGroupService;
import com.novoseltsev.dictionaryapi.service.TermService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;
    private final TermGroupService termGroupService;

    @Autowired
    public TermServiceImpl(
            TermRepository termRepository, TermGroupService termGroupService
    ) {
        this.termRepository = termRepository;
        this.termGroupService = termGroupService;
    }

    @Override
    @Transactional
    public Term createForTermGroup(Term term) {
        Long termGroupId = term.getTermGroup().getId();
        termGroupService.findById(termGroupId).addTerm(term);
        return termRepository.save(term);
    }

    @Override
    @Transactional
    public Term update(Term term) {
        Term savedTerm = findById(term.getId());
        savedTerm.setName(term.getName());
        savedTerm.setDefinition(term.getDefinition());
        return termRepository.save(savedTerm);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        termRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Term findById(Long id) {
        return termRepository.findById(id).orElseThrow(ExceptionUtils.OBJECT_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Term> findAllByTermGroupId(Long termGroupId) {
        return termRepository.findAllByTermGroupIdOrderById(termGroupId);
    }
}
