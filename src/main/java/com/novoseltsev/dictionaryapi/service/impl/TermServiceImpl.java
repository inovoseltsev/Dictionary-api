package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.dto.term.AnswerDto;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import com.novoseltsev.dictionaryapi.domain.status.TermAwareStatus;
import com.novoseltsev.dictionaryapi.exception.ObjectNotFoundException;
import com.novoseltsev.dictionaryapi.repository.TermRepository;
import com.novoseltsev.dictionaryapi.service.TermGroupService;
import com.novoseltsev.dictionaryapi.service.TermService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import static com.novoseltsev.dictionaryapi.domain.status.TermAwareStatus.BAD;
import static com.novoseltsev.dictionaryapi.domain.status.TermAwareStatus.PERFECT;

@Component
@Transactional
public class TermServiceImpl implements TermService {

    @Value("${upload.path}")
    private String uploadPath;

    private final TermRepository termRepository;
    private final TermGroupService termGroupService;
    private final MessageSourceAccessor messageAccessor;

    public TermServiceImpl(TermRepository termRepository, TermGroupService termGroupService,
                           MessageSourceAccessor messageAccessor) {
        this.termRepository = termRepository;
        this.termGroupService = termGroupService;
        this.messageAccessor = messageAccessor;
    }

    @Override
    public Term createForTermGroup(Term term) {
        Long termGroupId = term.getTermGroup().getId();
        term.setAwareStatus(BAD);
        termGroupService.findById(termGroupId).addTerm(term);
        return termRepository.save(term);
    }

    @Override
    public Term update(Term term) throws IOException {
        Term savedTerm = findById(term.getId());
        String imagePath = updateImagePathIfNeeded(term.getImagePath(), savedTerm.getImagePath());
        savedTerm.setName(term.getName());
        savedTerm.setDefinition(term.getDefinition());
        savedTerm.setKeyword(term.getKeyword());
        savedTerm.setImagePath(imagePath);
        return termRepository.save(savedTerm);
    }

    private String updateImagePathIfNeeded(String newPath, String previousPath) throws IOException {
        String imagePath = previousPath;
        if (newPath != null && !newPath.isBlank()) {
            Files.delete(Paths.get(previousPath));
            imagePath = newPath;
        }
        return imagePath;
    }

    @Override
    public void deleteById(Long id) {
        termRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Term findById(Long id) {
        String errorMessage = messageAccessor.getMessage("term.not.found");
        return termRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(errorMessage));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Term> findAllByTermGroupId(Long termGroupId) {
        return termRepository.findAllByTermGroupIdOrderByIdDesc(termGroupId);
    }

    @Override
    public void uploadTermImage(MultipartFile image, Term term) throws IOException {
        if (image != null) {
            File uploadDir = new File(uploadPath);
            if (!Files.exists(uploadDir.toPath())) {
                Files.createDirectory(uploadDir.toPath());
            }
            String fileUUID = UUID.randomUUID().toString();
            String uniqueFileName = fileUUID + "." + image.getOriginalFilename();
            String filePath = uploadPath + "/" + uniqueFileName;
            Files.write(Paths.get(filePath), image.getBytes());
            term.setImagePath(filePath);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Term> getDefaultStudySet(Long termGroupId) {
        List<Term> groupTerms = findAllByTermGroupId(termGroupId);
        return getUnlearnedTermsSortedByAwareStatus(groupTerms);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Term> getStudySetWithKeywords(Long groupId) {
        List<Term> terms = findAllByTermGroupId(groupId);
        List<Term> groupTerms = terms.stream()
                .filter(el -> Objects.nonNull(el.getKeyword()))
                .collect(Collectors.toList());
        return getUnlearnedTermsSortedByAwareStatus(groupTerms);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Term> getStudySetWithImages(Long termGroupId) {
        List<Term> terms = findAllByTermGroupId(termGroupId);
        List<Term> groupTerms = terms.stream()
                .filter(el -> Objects.nonNull(el.getImagePath()))
                .collect(Collectors.toList());
        return getUnlearnedTermsSortedByAwareStatus(groupTerms);
    }

    private List<Term> getUnlearnedTermsSortedByAwareStatus(List<Term> terms) {
        return terms.stream()
                .filter(term -> !term.getAwareStatus().equals(PERFECT))
                .sorted(Comparator.comparing(Term::getAwareStatus))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<List<Term>> getStudySetInChunks(Long termGroupId) {
        List<Term> terms = findAllByTermGroupId(termGroupId);
        List<Term> unlearnedTerms = terms.stream()
                .filter(el -> !el.getAwareStatus().equals(PERFECT))
                .collect(Collectors.toList());
        Collections.shuffle(unlearnedTerms);
        return ListUtils.partition(unlearnedTerms, 4);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AnswerDto> getAnswersForTerm(Long termId) {
        List<Term> answerVariants = getAnswerVariants(termId);
        List<AnswerDto> answers = answerVariants.stream()
                .map(term -> new AnswerDto(term, term.getId().equals(termId)))
                .collect(Collectors.toList());
        Collections.shuffle(answers);
        return answers;
    }

    private List<Term> getAnswerVariants(Long termId) {
        Term correctVariant = findById(termId);
        List<Term> answerVariants = correctVariant.getTermGroup().getTerms();
        Collections.shuffle(answerVariants);
        answerVariants = answerVariants.stream()
                .filter(el -> !el.getId().equals(termId))
                .limit(3)
                .collect(Collectors.toList());
        answerVariants.add(correctVariant);
        return answerVariants;
    }

    @Override
    public void updateAwareStatus(Long termId, TermAwareStatus awareStatus) {
        Term term = findById(termId);
        term.setAwareStatus(awareStatus);
        termRepository.save(term);
    }

    @Override
    public void resetAwareStatusForAllInTermGroup(Long groupId) {
        List<Term> terms = findAllByTermGroupId(groupId);
        terms.forEach(el -> el.setAwareStatus(TermAwareStatus.BAD));
        termRepository.saveAll(terms);
    }
}
