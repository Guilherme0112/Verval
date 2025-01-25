package com.br.verval.controllers.Tokens;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.br.verval.models.ConfirmEmail;
import com.br.verval.models.Usuario;
import com.br.verval.repositorys.ConfirmEmailRepository;
import com.br.verval.repositorys.UsuarioRepository;
import com.br.verval.service.UsuarioService;

@RestController
public class TokensController {
    
    private final ConfirmEmailRepository confirmEmailRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public TokensController(ConfirmEmailRepository confirmEmailRepository,
                            UsuarioRepository usuarioRepository,
                            UsuarioService usuarioService){
        this.confirmEmailRepository = confirmEmailRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }
    
    @GetMapping("/api/confirmation_email/{token}")
    public ResponseEntity<?> ConfirmationEmail(@PathVariable("token") String token) throws Exception {

        try {

           List<ConfirmEmail> user_token = confirmEmailRepository.findByToken(token);

            // Verifica se a conta existe/inativa
            if (user_token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("Erro", "Token inválido"));
            }

            // Converte o token para o objeto
            ConfirmEmail token_obj = user_token.get(0);

            // Busca o usuário do token no banco de dados
            List<Usuario> user_inative = usuarioRepository.findByEmail(token_obj.getEmail_user(), false);

            if(user_inative.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Erro", "Esta conta não existe ou já está ativa"));
            }

            // Obtem o objeto de user
            Usuario user = user_inative.get(0);

            // Ativa a conta do usuário
            usuarioService.trocarStatus(user);

            // Deleta o registro do token
            confirmEmailRepository.delete(token_obj);

            return ResponseEntity.ok(Map.of("OK", "E-mail verificado com sucesso"));

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("Erro", "Ocorreu algum erro. Tente novamente mais tarde"));
        }
    }
}
