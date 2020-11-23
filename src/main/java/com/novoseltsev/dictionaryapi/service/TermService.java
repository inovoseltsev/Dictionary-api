package com.novoseltsev.dictionaryapi.service;

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

    String uploadTermImage(MultipartFile image) throws IOException;

    List<Term> createStudySetFromTermGroup(Long termGroupId);

    List<Term> createStudySetWithKeywordsFromTermGroup(Long termGroupId);

    List<Term> createStudySetWithImagesFromTermGroup(Long termGroupId);

    List<List<Term>> createStudySetInChunksFromTermGroup(Long termGroupId);

    void changeAwareStatus(Long termId, TermAwareStatus awareStatus);

    void resetAwareStatusForAllInTermGroup(Long termGroupId);
}
