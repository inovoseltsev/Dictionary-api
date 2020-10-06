package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.dto.user.request.ChangePasswordRequest;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User create(User user);

    User update(User user);

    void changePassword(Long userId, ChangePasswordRequest passwordRequest);

    void markAsDeletedById(Long id);

    void deleteById(Long id);

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);
}
