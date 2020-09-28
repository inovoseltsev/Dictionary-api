package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.entity.WordSet;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface WordSetService {

    WordSet createForUser(WordSet wordSet, Long userId);

    WordSet update(WordSet wordSet);

    void deleteById(Long id);

    WordSet findById(Long id);

    List<WordSet> findAllByUserId(Long userId);
}
