package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.entity.Term;
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

    List<Term> findAllByTermGroupId(Long termGroupId);

    String uploadTermImage (MultipartFile image) throws IOException;
}
