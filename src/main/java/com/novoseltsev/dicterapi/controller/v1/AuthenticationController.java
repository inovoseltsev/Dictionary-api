package com.novoseltsev.dicterapi.controller.v1;

import com.novoseltsev.dicterapi.controller.v1.api.AuthenticationApi;
import com.novoseltsev.dicterapi.domain.dto.auth.AuthenticationDto;
import com.novoseltsev.dicterapi.service.AuthenticationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("authentication")
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    @Override
    @PostMapping
    public String authenticate(@Valid @RequestBody AuthenticationDto authenticationDto) {
        return authenticationService.authenticateUser(authenticationDto.getLogin(), authenticationDto.getPassword());
    }
}
