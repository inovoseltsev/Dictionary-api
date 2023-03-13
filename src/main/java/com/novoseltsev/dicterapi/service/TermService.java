package com.novoseltsev.dicterapi.service;

import com.novoseltsev.dicterapi.domain.entity.Term;
import com.novoseltsev.dicterapi.domain.model.study.StudyTerm;
import com.novoseltsev.dicterapi.domain.status.TermAwareStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface TermService {

    Term createForTermGroup(Term term);

    Term update(Term term) throws IOException;

    void deleteById(Long id);

    Term findById(Long id);

    List<Term> findAllByTermGroupId(Long groupId);

    void uploadTermImage(MultipartFile image, Term term) throws IOException;

    List<StudyTerm> getDefaultStudySet(Long termGroupId);

    List<StudyTerm> getStudySetWithKeywords(Long termGroupId);

    List<StudyTerm> getStudySetWithImages(Long termGroupId);

    List<List<StudyTerm>> getStudySetInChunks(Long termGroupId);

    void updateAwareStatus(Long termId, TermAwareStatus awareStatus);

    void resetAwareStatusForAllInTermGroup(Long termGroupId);
}
