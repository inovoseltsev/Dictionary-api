package com.novoseltsev.dicterapi.service.impl;

import com.novoseltsev.dicterapi.domain.entity.User;
import com.novoseltsev.dicterapi.domain.role.UserRole;
import com.novoseltsev.dicterapi.domain.status.UserStatus;
import com.novoseltsev.dicterapi.exception.InvalidOldPasswordException;
import com.novoseltsev.dicterapi.exception.LoginIsAlreadyUsedException;
import com.novoseltsev.dicterapi.exception.ObjectNotFoundException;
import com.novoseltsev.dicterapi.exception.UserAccessForbiddenException;
import com.novoseltsev.dicterapi.repository.UserRepository;
import com.novoseltsev.dicterapi.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new LoginIsAlreadyUsedException("Login is already used");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User savedUser = findById(user.getId());
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        return userRepository.save(savedUser);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = findById(userId);
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new InvalidOldPasswordException("Old password is incorrect");
        }
    }

    @Override
    public void updateUserStatus(Long userId, UserStatus status) {
        User user = findById(userId);
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
            .filter(user -> !user.getRole().equals(UserRole.ADMIN))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new UserAccessForbiddenException("User is not allowed");
        }
        return user;
    }
}
