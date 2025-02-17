package com.br.verval.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {


    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    
    public AuthController(UsuarioService usuarioService,
                          UsuarioRepository usuarioRepository){

        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

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
    public ResponseEntity<?> Login(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response, HttpServletRequest request) {

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

        // Gera a resposta/estrutura do cookie
        ResponseCookie cookie = ResponseCookie.from("token", token)
            .httpOnly(true)  
            .secure(false)     
            .path("/")          
            .maxAge(60 * 60 * 4) 
            .sameSite("Strict") 
            .build();

        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok()
                            .body(Map.of("status", "Toke gerado com sucesso"));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> Validate(HttpServletRequest request){

        try {

            // Busca o token nos cookies
           String token = JWTUtil.getCookie(request, "token");

           // Verifica se existe algum token
            if(token.isEmpty()){
                return ResponseEntity.status(401).body(Map.of("erro", "Usuário não autenticado"));
            }
    
            // Faz o decode do token para verificar a autenticidade do token
            if(JWTUtil.decodeJWT(token) == null){
                return ResponseEntity.badRequest().body(Map.of("erro", "Token inválido"));
            }
    
            // Verifica se o token está expirado
            if(JWTUtil.tokenExpired(token)){
                return ResponseEntity.badRequest().body(Map.of("erro", "Token expirado"));
            }


            return ResponseEntity.ok().body(Map.of("status", "Usuário autenticado"));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
       

    }
}
