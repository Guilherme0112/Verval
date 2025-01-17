package com.br.verval.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.verval.models.dto.LoginRequest;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    @PostMapping("/login")
    public LoginRequest login(@RequestBody LoginRequest loginRequest) {
        return loginRequest;
    }
}
