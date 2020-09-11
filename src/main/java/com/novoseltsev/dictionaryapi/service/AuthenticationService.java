package com.novoseltsev.dictionaryapi.service;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    Map<Object, Object> authenticate(String login, String password);
}
