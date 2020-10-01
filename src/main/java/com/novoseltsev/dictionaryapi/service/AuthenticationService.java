package com.novoseltsev.dictionaryapi.service;

import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    String authenticate(String login, String password);
}
