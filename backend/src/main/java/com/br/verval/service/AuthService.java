package com.br.verval.service;

import org.springframework.stereotype.Service;

import com.br.verval.models.dto.LoginRequest;

@Service
public class AuthService {
    
    public static Boolean validateSession(LoginRequest loginRequest){

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        

        return Boolean.FALSE;
    }
}
