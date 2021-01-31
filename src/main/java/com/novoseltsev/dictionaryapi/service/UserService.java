package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.domain.status.UserStatus;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User create(User user);

    User update(User user);

    void updatePassword(Long userId, String oldPassword, String newPassword);

    void updateUserStatus(Long userId, UserStatus status);

    void deleteById(Long id);

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);
}
