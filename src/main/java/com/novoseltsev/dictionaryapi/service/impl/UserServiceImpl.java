package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.dto.request.ChangePasswordDto;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.domain.status.UserStatus;
import com.novoseltsev.dictionaryapi.exception.InvalidOldPasswordException;
import com.novoseltsev.dictionaryapi.exception.LoginIsAlreadyUsedException;
import com.novoseltsev.dictionaryapi.exception.util.ExceptionUtils;
import com.novoseltsev.dictionaryapi.exception.util.MessageCause;
import com.novoseltsev.dictionaryapi.repository.UserRepository;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User create(User user) {
        checkLoginUniqueness(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    private void checkLoginUniqueness(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new LoginIsAlreadyUsedException(MessageCause.LOGIN_IS_ALREADY_USED);
        }
    }

    @Override
    @Transactional
    public User update(User user) {
        User savedUser = findById(user.getId());
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        return userRepository.save(savedUser);
    }

    @Override
    @Transactional
    public void changePassword(
            Long userId, ChangePasswordDto passwordDto) {
        User user = findById(userId);
        checkIfValidOldPassword(user, passwordDto.getOldPassword());
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

    private void checkIfValidOldPassword(User user, String oldPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getFirstName())) {
            throw new InvalidOldPasswordException(MessageCause.INVALID_OLD_PASSWORD);
        }
    }

    @Override
    @Transactional
    public void markAsDeletedById(Long id) {
        User user = findById(id);
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(ExceptionUtils.OBJECT_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(ExceptionUtils.OBJECT_NOT_FOUND);
    }
}
