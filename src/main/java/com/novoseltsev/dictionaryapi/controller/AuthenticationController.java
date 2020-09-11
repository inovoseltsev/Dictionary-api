package com.novoseltsev.dictionaryapi.controller;

import com.novoseltsev.dictionaryapi.domain.dto.AuthDto;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @PostMapping
    public void authenticate(@Valid AuthDto authDto) {

    }

}
