package com.novoseltsev.dicterapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novoseltsev.dicterapi.repository.UserRepository;
import com.novoseltsev.dicterapi.security.jwt.JwtProvider;
import com.novoseltsev.dicterapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public String authenticateUser(String login, String password) {
        var user = userRepository.findByLogin(login);

        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new BadCredentialsException("Login or password is invalid");
        }

        var token = jwtProvider.createToken(user.get().getId(), user.get().getRole());
        var authentication = jwtProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return createTokenResponse(token);
    }

    private String createTokenResponse(String token) {
        try {
            return new ObjectMapper().writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot create token response", e);
        }
    }
}
