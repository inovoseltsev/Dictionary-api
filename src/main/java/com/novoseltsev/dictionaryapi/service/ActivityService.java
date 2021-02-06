package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.entity.Activity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ActivityService {

    Activity create(Activity activity);

    Activity update(Activity activity);

    void deleteById(Long id);

    Activity findById(Long id);

    List<Activity> findAllByUserId(Long userId);
}
