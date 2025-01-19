package com.br.verval.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.verval.models.Usuario;
import com.br.verval.models.dto.ErrorValidationDTO;
import com.br.verval.models.dto.LoginRequestDTO;
import com.br.verval.models.dto.ResponseDTO;
import com.br.verval.repositorys.UsuarioRepository;
import com.br.verval.utils.JWTUtil;
import com.br.verval.utils.Util;

import jakarta.validation.Valid;



@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid Usuario usuario, BindingResult br){

        if(br.hasErrors()){
            return ResponseEntity.badRequest().body(new ErrorValidationDTO("Erro validação", br.getAllErrors()));
        }

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {

        String email = loginRequest.getEmail();
        String senha = loginRequest.getPassword();

        if(usuarioRepository.findByEmail(email, true).isEmpty()){
            return ResponseEntity.ok(new ResponseDTO("Erro", "As credenciais estão incorretas"));
        }

        // Converte o List para o objeto Usuario
        List<Usuario> usuarioBD = usuarioRepository.findByEmail(email, true);
        Usuario usuarioObj = usuarioBD.get(0);

        if(!Util.checkPassword(usuarioObj.getSenha_usuario(), senha)){
            return ResponseEntity.ok(new ResponseDTO("Erro", "As credenciais estão incorretas"));
        }

        String token = JWTUtil.gerarToken(email);


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
    }
}
