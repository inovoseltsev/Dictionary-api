package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.dto.term.AnswerDto;
import com.novoseltsev.dictionaryapi.domain.entity.Term;
import com.novoseltsev.dictionaryapi.domain.status.TermAwareStatus;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface TermService {

    Term createForTermGroup(Term term);

    Term update(Term term) throws IOException;

    void deleteById(Long id);

    Term findById(Long id);

    List<Term> findAllByTermGroupId(Long groupId);

    void uploadTermImage(MultipartFile image, Term term) throws IOException;

    List<Term> getDefaultStudySet(Long termGroupId);

    List<Term> getStudySetWithKeywords(Long termGroupId);

    List<Term> getStudySetWithImages(Long termGroupId);

    List<List<Term>> getStudySetInChunks(Long termGroupId);

    List<AnswerDto> getAnswersForTerm(Long termId);

    void updateAwareStatus(Long termId, TermAwareStatus awareStatus);

    void resetAwareStatusForAllInTermGroup(Long termGroupId);
}
