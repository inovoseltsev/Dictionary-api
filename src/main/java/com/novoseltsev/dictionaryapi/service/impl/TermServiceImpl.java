package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.Term;
import com.novoseltsev.dictionaryapi.domain.status.TermAwareStatus;
import com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils;
import com.novoseltsev.dictionaryapi.repository.TermRepository;
import com.novoseltsev.dictionaryapi.service.TermGroupService;
import com.novoseltsev.dictionaryapi.service.TermService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import static com.novoseltsev.dictionaryapi.domain.status.TermAwareStatus.PERFECT;

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

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    @Transactional
    public Term createForTermGroup(Term term) {
        Long termGroupId = term.getTermGroup().getId();
        termGroupService.findById(termGroupId).addTerm(term);
        return termRepository.save(term);
    }

    @Override
    @Transactional
    public Term update(Term term) throws IOException {
        Term savedTerm = findById(term.getId());
        changePreviousImage(term.getImagePath(), savedTerm.getImagePath());
        savedTerm.setName(term.getName());
        savedTerm.setDefinition(term.getDefinition());
        savedTerm.setKeyword(term.getKeyword());
        savedTerm.setImagePath(term.getImagePath());
        return termRepository.save(savedTerm);
    }

    private void changePreviousImage(String newPath, String previousPath) throws IOException {
        if (!StringUtils.isEmpty(newPath)) {
            Files.delete(Paths.get(previousPath));
        }
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

    @Override
    public String uploadTermImage(MultipartFile image) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!Files.exists(uploadDir.toPath())) {
            Files.createDirectory(uploadDir.toPath());
        }
        String fileUUID = UUID.randomUUID().toString();
        String uniqueFileName = fileUUID + "." + image.getOriginalFilename();
        String filePath = uploadPath + "/" + uniqueFileName;
        Files.write(Paths.get(filePath), image.getBytes());
        return filePath;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Term> createStudySetFromTermGroup(Long termGroupId) {
        List<Term> groupTerms = findAllByTermGroupId(termGroupId);
        return getUnlearnedTermsSortedByAwareStatusFrom(groupTerms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Term> createStudySetWithKeywordsFromTermGroup(Long groupId) {
        List<Term> terms = findAllByTermGroupId(groupId);
        List<Term> groupTerms = terms.stream().filter(el -> Objects.nonNull(el.getKeyword()))
                .collect(Collectors.toList());
        return getUnlearnedTermsSortedByAwareStatusFrom(groupTerms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Term> createStudySetWithImagesFromTermGroup(Long termGroupId) {
        List<Term> terms = findAllByTermGroupId(termGroupId);
        List<Term> groupTerms = terms.stream().filter(el -> Objects.nonNull(el.getImagePath()))
                .collect(Collectors.toList());
        return getUnlearnedTermsSortedByAwareStatusFrom(groupTerms);
    }

    private List<Term> getUnlearnedTermsSortedByAwareStatusFrom(List<Term> terms) {
        List<Term> unlearnedTerms = new ArrayList<>();
        for (Term term : terms) {
            if (!term.getAwareStatus().equals(PERFECT)) {
                unlearnedTerms.add(term);
            }
        }
        unlearnedTerms.sort(Comparator.comparing(Term::getAwareStatus));
        return unlearnedTerms;
    }

    @Override
    @Transactional(readOnly = true)
    public List<List<Term>> createStudySetInChunksFromTermGroup(Long termGroupId) {
        List<Term> terms = findAllByTermGroupId(termGroupId);
        List<Term> unlearnedTerms = terms.stream()
                .filter(el -> !el.getAwareStatus().equals(PERFECT)).collect(Collectors.toList());
        Collections.shuffle(unlearnedTerms);
        return ListUtils.partition(unlearnedTerms, 4);
    }

    @Override
    @Transactional
    public void changeAwareStatus(Long termId, TermAwareStatus awareStatus) {
        Term term = findById(termId);
        term.setAwareStatus(awareStatus);
    }

    @Override
    @Transactional
    public void resetAwareStatusForAllInTermGroup(Long groupId) {
        List<Term> terms = findAllByTermGroupId(groupId);
        terms.forEach(el -> el.setAwareStatus(TermAwareStatus.BAD));
        termRepository.saveAll(terms);
    }
}
