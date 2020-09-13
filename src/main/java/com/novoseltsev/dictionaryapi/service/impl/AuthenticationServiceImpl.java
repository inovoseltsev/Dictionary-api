package com.novoseltsev.dictionaryapi.service.impl;

import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.exception.util.MessageCause;
import com.novoseltsev.dictionaryapi.repository.UserRepository;
import com.novoseltsev.dictionaryapi.security.jwt.JwtProvider;
import com.novoseltsev.dictionaryapi.service.AuthenticationService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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
    public Map<Object, Object> authenticate(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        User user = optionalUser.orElse(null);
        if (!optionalUser.isPresent() || !passwordEncoder.matches(password,
                user.getPassword())) {
            throw new BadCredentialsException(MessageCause.BAD_CREDENTIALS);
        }
        String token = jwtProvider.createToken(user.getId(), user.getRole());
        SecurityContextHolder.getContext()
                .setAuthentication(jwtProvider.getAuthentication(token));
        return new HashMap<Object, Object>() {{
            put("token", token);
        }};
    }
}
