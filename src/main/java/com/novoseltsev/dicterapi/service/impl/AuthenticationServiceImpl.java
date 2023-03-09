package com.novoseltsev.dicterapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novoseltsev.dicterapi.domain.entity.User;
import com.novoseltsev.dicterapi.repository.UserRepository;
import com.novoseltsev.dicterapi.security.jwt.JwtProvider;
import com.novoseltsev.dicterapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final MessageSourceAccessor messageAccessor;

    @Override
    public String login(String login, String password) {
        User user = userRepository.findByLogin(login).orElse(null);
        checkUserCredentialsValidity(user, password);
        String token = authenticate(user);
        return convertToJsonString(token);
    }

    private void checkUserCredentialsValidity(User user, String password) {
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(messageAccessor.getMessage("bad.credentials"));
        }
    }

    private String authenticate(User user) {
        String token = jwtProvider.createToken(user.getId(), user.getRole());
        Authentication authentication = jwtProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return token;
    }

    private String convertToJsonString(String token) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(messageAccessor.getMessage("create.token.error"), e);
        }
    }
}
