package com.br.verval.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {
    
    @GetMapping("/api/usuarios")
    public String[] getUsuarios() {
        String[] msg = {"Olá", "Mundo"};

        return msg;
    }
}
