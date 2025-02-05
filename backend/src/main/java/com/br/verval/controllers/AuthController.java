package com.br.verval.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.verval.models.Usuario;
import com.br.verval.models.dto.LoginRequestDTO;
import com.br.verval.repositorys.UsuarioRepository;
import com.br.verval.service.UsuarioService;
import com.br.verval.utils.JWTUtil;
import com.br.verval.utils.Util;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody @Valid Usuario usuario, BindingResult br) throws Exception {

        try {
            // Verifica se existe erros
            if (br.hasErrors()) {

                // Formata as mensagens de erros adicionando um índice com o nome do campo e a mensagem
                Map<String, String> errorMap = new HashMap<>();

                br.getAllErrors().forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errorMap.put(fieldName, errorMessage);
                });

                return ResponseEntity.badRequest().body(Map.of("validation", errorMap));
            }

            // Retorna se conseguiu criar a conta ou se não conseguiu
            return usuarioService.createUser(usuario);

        } catch (Exception e) {

            System.out.println("Erro_Exceptions: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Erro", e.getMessage()));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequestDTO loginRequest) {

        String email = loginRequest.getEmail();
        String senha = loginRequest.getPassword();

        // Verifica se existe uma conta ativa com o e-mail do usuario
        if (usuarioRepository.findByEmail(email, true).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "As credenciais estão incorretas"));
        }

        // Converte o List para o objeto Usuario
        List<Usuario> usuarioBD = usuarioRepository.findByEmail(email, true);
        Usuario usuarioObj = usuarioBD.get(0);

        // Verifica se a senha corresponde ao hash do banco de dados
        if (!Util.checkPassword(usuarioObj.getSenha(), senha)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "As credenciais estão incorretas"));
        }

        // Gera o token
        String token = JWTUtil.gerarToken(email);

        ResponseCookie cookie = ResponseCookie.from("token", token)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .sameSite("Strict")
        .maxAge(14400)
        .build();

        return ResponseEntity.ok()
                            .header("Set-Cookie", cookie.toString())
                            .body(Map.of("status", 200));
    }
}
