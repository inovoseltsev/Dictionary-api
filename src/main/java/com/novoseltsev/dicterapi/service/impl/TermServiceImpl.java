package com.novoseltsev.dicterapi.service.impl;

import com.novoseltsev.dicterapi.domain.entity.Term;
import com.novoseltsev.dicterapi.domain.model.study.Answer;
import com.novoseltsev.dicterapi.domain.model.study.StudyTerm;
import com.novoseltsev.dicterapi.domain.status.TermAwareStatus;
import com.novoseltsev.dicterapi.exception.ObjectNotFoundException;
import com.novoseltsev.dicterapi.repository.TermRepository;
import com.novoseltsev.dicterapi.service.TermGroupService;
import com.novoseltsev.dicterapi.service.TermService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

import static com.novoseltsev.dicterapi.domain.status.TermAwareStatus.BAD;
import static com.novoseltsev.dicterapi.domain.status.TermAwareStatus.PERFECT;

@RequiredArgsConstructor
@Component
@Transactional
public class TermServiceImpl implements TermService {

    @Value("${upload.path}")
    private String uploadPath;

    private final TermRepository termRepository;
    private final TermGroupService termGroupService;
    private final MessageSourceAccessor messageAccessor;

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
            if (previousPath != null && !previousPath.isBlank()) {
                Files.delete(Paths.get(previousPath));
            }
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
    public List<StudyTerm> getDefaultStudySet(Long termGroupId) {
        var groupTerms = findAllByTermGroupId(termGroupId);
        return createSortedStudySet(groupTerms, groupTerms);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<StudyTerm> getStudySetWithKeywords(Long groupId) {
        var groupTerms = findAllByTermGroupId(groupId);
        var termsWithKeywords = groupTerms.stream()
            .filter(el -> Objects.nonNull(el.getKeyword()) && !el.getKeyword().isBlank())
            .collect(Collectors.toList());
        return createSortedStudySet(termsWithKeywords, groupTerms);
    }


    //TODO Make images be available by url
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<StudyTerm> getStudySetWithImages(Long termGroupId) {
        var groupTerms = findAllByTermGroupId(termGroupId);
        var termsWithImage = groupTerms.stream()
            .filter(el -> Objects.nonNull(el.getImagePath()))
            .collect(Collectors.toList());
        return createSortedStudySet(termsWithImage, groupTerms);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<List<StudyTerm>> getStudySetInChunks(Long termGroupId) {
        var groupTerms = findAllByTermGroupId(termGroupId);
        var unlearnedTerms = groupTerms.stream()
            .filter(el -> !el.getAwareStatus().equals(PERFECT))
            .collect(Collectors.toList());
        Collections.shuffle(unlearnedTerms);
        return ListUtils.partition(createStudySet(unlearnedTerms, groupTerms), 3);
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

    private List<StudyTerm> createStudySet(List<Term> unlearnedTerms, List<Term> groupTerms) {
        return unlearnedTerms.stream()
            .map(it -> new StudyTerm(it, generateAnswersForTerm(it, groupTerms)))
            .collect(Collectors.toList());
    }

    private List<StudyTerm> createSortedStudySet(List<Term> unlearnedTerms, List<Term> groupTerms) {
        var sortedUnlearnedTerms = getUnlearnedTermsSortedByAwareStatus(unlearnedTerms);
        return createStudySet(sortedUnlearnedTerms, groupTerms);
    }

    private List<Term> getUnlearnedTermsSortedByAwareStatus(List<Term> terms) {
        return terms.stream()
            .filter(term -> !term.getAwareStatus().equals(PERFECT))
            .sorted(Comparator.comparing(Term::getAwareStatus))
            .collect(Collectors.toList());
    }

    private List<Answer> generateAnswersForTerm(Term term, List<Term> groupTerms) {
        var shuffledTerms = new ArrayList<>(groupTerms);
        Collections.shuffle(shuffledTerms);

        var answerTerms = shuffledTerms.stream()
            .filter(it -> !it.getId().equals(term.getId()))
            .limit(3)
            .collect(Collectors.toList());
        answerTerms.add(term);
        Collections.shuffle(answerTerms);

        return answerTerms.stream()
            .map(it -> new Answer(it.getId(), it.getDefinition(), it.getId().equals(term.getId())))
            .collect(Collectors.toList());
    }
}
