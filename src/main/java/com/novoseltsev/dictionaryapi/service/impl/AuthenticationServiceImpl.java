package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.exception.util.MessageCause;
import com.novoseltsev.dictionaryapi.repository.UserRepository;
import com.novoseltsev.dictionaryapi.security.jwt.JwtProvider;
import com.novoseltsev.dictionaryapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthenticationServiceImpl(
            UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtProvider jwtProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String authenticate(String login, String password) {
        User user = userRepository.findByLogin(login).orElse(null);
        checkUserCredentialsValidity(user, password);
        String token = jwtProvider.createToken(user.getId(), user.getRole());
        Authentication authentication = jwtProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return token;
    }

    private void checkUserCredentialsValidity(User user, String password) {
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(MessageCause.BAD_CREDENTIALS);
        }
    }
}
