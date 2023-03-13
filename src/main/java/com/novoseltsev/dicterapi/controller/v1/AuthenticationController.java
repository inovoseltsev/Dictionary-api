package com.novoseltsev.dicterapi.controller.v1;

import com.novoseltsev.dicterapi.domain.dto.auth.AuthDto;
import com.novoseltsev.dicterapi.service.AuthenticationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public String authenticate(@Valid @RequestBody AuthDto authDto) {
        return authenticationService.login(authDto.getLogin(), authDto.getPassword());
    }
}
