package com.novoseltsev.dicterapi.service;

import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    String authenticateUser(String login, String password);
}
