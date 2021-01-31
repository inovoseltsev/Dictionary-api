package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.entity.Specialization;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface SpecializationService {

    Specialization create(Specialization specialization);

    Specialization update(Specialization specialization);

    void deleteById(Long id);

    Specialization findById(Long id);

    List<Specialization> findAllByUserId(Long userId);
}
