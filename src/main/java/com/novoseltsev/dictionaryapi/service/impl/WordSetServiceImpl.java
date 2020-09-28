package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.domain.entity.WordSet;
import com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils;
import com.novoseltsev.dictionaryapi.repository.WordSetRepository;
import com.novoseltsev.dictionaryapi.service.UserService;
import com.novoseltsev.dictionaryapi.service.WordSetService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class WordSetServiceImpl implements WordSetService {

    private final WordSetRepository wordSetRepository;
    private final UserService userService;

    @Autowired
    public WordSetServiceImpl(
            WordSetRepository wordSetRepository, UserService userService
    ) {
        this.wordSetRepository = wordSetRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public WordSet createForUser(WordSet wordSet, Long userId) {
        User user = userService.findById(userId);
        user.addWordSet(wordSet);
        return wordSetRepository.save(wordSet);
    }

    @Override
    @Transactional
    public WordSet update(WordSet wordSet) {
        WordSet savedWordSet = findById(wordSet.getId());
        savedWordSet.setName(wordSet.getName());
        savedWordSet.setDescription(wordSet.getDescription());
        return wordSetRepository.save(savedWordSet);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        wordSetRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public WordSet findById(Long id) {
        return wordSetRepository.findById(id)
                .orElseThrow(ExceptionUtils.OBJECT_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WordSet> findAllByUserId(Long userId) {
        return userService.findById(userId).getWordSets();
    }
}
