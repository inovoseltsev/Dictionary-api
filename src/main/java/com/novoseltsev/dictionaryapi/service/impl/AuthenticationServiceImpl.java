package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.repository.UserRepository;
import com.novoseltsev.dictionaryapi.security.jwt.JwtProvider;
import com.novoseltsev.dictionaryapi.service.AuthenticationService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(
            UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JwtProvider jwtProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Map<Object, Object> authenticate(String login, String password) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isPresent() && passwordEncoder.matches(password,
                user.get().getPassword())) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, password)
            );
            String token = jwtProvider.createToken(user.get().getId(), user.get().getRole());
            Map<Object, Object> tokenResponse = new HashMap<>();
            tokenResponse.put("token", token);
            return tokenResponse;
        } else {
            throw new BadCredentialsException("Login or password is invalid");
        }
    }
}
