package com.br.verval.controllers.Tokens;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.br.verval.models.ConfirmEmail;
import com.br.verval.models.Usuario;
import com.br.verval.repositorys.ConfirmEmailRepository;
import com.br.verval.repositorys.UsuarioRepository;
import com.br.verval.service.UsuarioService;
import com.br.verval.utils.Util;

@RestController
public class TokensController {
    
    @Autowired
    private ConfirmEmailRepository confirmEmailRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/api/confirmation_email/{token}")
    public ResponseEntity<?> ConfirmationEmail(@PathVariable("token") String token) throws Exception {

        try {

           List<ConfirmEmail> user_token = confirmEmailRepository.findByToken(token);

            // Verifica se a conta existe/inativa
            if (user_token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("Erro", "Token inválido"));
            }

            // Altera o status para conta ativa
            ConfirmEmail token_obj = user_token.get(0);

            
            // usuarioService.trocarStatus(usuario_obj);

            return ResponseEntity.ok(Map.of("OK", "Status alterado com sucesso"));

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("Erro", "Ocorreu algum erro. Tente novamente mais tarde"));
        }
    }
}
