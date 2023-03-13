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
import org.springframework.context.support.MessageSourceAccessor;
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
    private final MessageSourceAccessor messageAccessor;

    @Override
    public User create(User user) {
        checkLoginUniqueness(user.getLogin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    private void checkLoginUniqueness(String login) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new LoginIsAlreadyUsedException(messageAccessor.getMessage("login.is.used"));
        }
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
        checkIfValidOldPassword(user, oldPassword);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private void checkIfValidOldPassword(User user, String oldPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidOldPasswordException(messageAccessor.getMessage("incorrect.old.password"));
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
        String errorMessage = messageAccessor.getMessage("user.not.found");
        User user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(errorMessage));
        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new UserAccessForbiddenException(messageAccessor.getMessage("no.user.account.access"));
        }
        return user;
    }
}
