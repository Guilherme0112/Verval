package com.br.verval.controllers.Tokens;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.br.verval.models.Usuario;
import com.br.verval.repositorys.UsuarioRepository;
import com.br.verval.service.UsuarioService;
import com.br.verval.utils.Util;

@RestController
public class TokensController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/api/confirmation_email/{token}")
    public ResponseEntity<?> confirmationEmail(@PathVariable("token") String token) throws Exception {

        try {

            String user_email = Util.decrypt(token);

            List<Usuario> usuario_inativo = usuarioRepository.findByEmail(user_email, false);

            // Verifica se a conta existe/inativa
            if (usuario_inativo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("Erro", "Este e-mail não está ativo ou registrado"));
            }

            // Altera o status para conta ativa
            Usuario usuario_obj = usuario_inativo.get(0);
            usuarioService.trocarStatus(usuario_obj);

            return ResponseEntity.ok(Map.of("OK", "Status alterado com sucesso"));

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("Erro", "Ocorreu algum erro. Tente novamente mais tarde"));
        }
    }
}
