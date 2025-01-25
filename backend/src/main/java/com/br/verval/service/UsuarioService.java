package com.br.verval.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.verval.models.ConfirmEmail;
import com.br.verval.models.Usuario;
import com.br.verval.repositorys.ConfirmEmailRepository;
import com.br.verval.repositorys.UsuarioRepository;
import com.br.verval.utils.Util;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfirmEmailRepository confirmEmailRepository;

    private static final LocalDateTime EXPIRATION = LocalDateTime.now().plusMinutes(5);
  
    /***
     * Cria o registro do usuário
     * 
     * @param usuario Recebe o objeto do usuário
     * @return Retorna TRUE caso tenha criado, FALSE quando não foi possível
     */
    public ResponseEntity<?> createUser(Usuario usuario) throws Exception{

        try {
            
            // Verifica se o e-mail já está em uso
            if(!usuarioRepository.findByEmail(usuario.getEmail(), true).isEmpty()){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Erro", "Este e-mail já está em uso"));
            }

            // Verifica se a conta está registrada como inativa
            if(!usuarioRepository.findByEmail(usuario.getEmail(), false).isEmpty()){
                
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Erro", "Esta conta está inativa"));
            }

            // Pega a senha do usuário e criptografa
            String userPass = usuario.getSenha();
            usuario.setSenha(Util.Bcrypt(userPass));

            // Gera o token
            String token = Util.generateToken();

            // Cria o objeto do token para salvar no banco de dados
            ConfirmEmail token_obj = new ConfirmEmail();
            token_obj.setToken(token);
            token_obj.setEmail_user(usuario.getEmail());
            token_obj.setExpire_in(EXPIRATION);

            confirmEmailRepository.save(token_obj);

            String email = """
                <html>
                    <head>
                        <meta charset="UTF-8">
                        <title>Confirmação de E-mail</title>
                    </head>
                    <body>
                        <h1>Verificação por e-mail</h1>
                        <p>Por favor, clique no link abaixo para confirmar seu e-mail:</p>
                        <a href='http://127.0.0.1:3000/mails/confirmation_email/""" + token + """
                        '
                           style='display: inline-block; background-color: #4CAF50; color: white; text-decoration: none; padding: 10px 20px; border-radius: 5px;'>
                           Confirmar Email
                        </a>
                        <br>
                        <p>Se você não registrou seu e-mail em nosso site, ignore esta mensagem.</p>
                    </body>
                </html>
                """;
            
            // Envia um e-mail de confirmação para o usuário
            emailService.sendEmail(usuario.getEmail(), "Verificação do e-mail", email);

            // Salva o usuário no banco de dados
            usuarioRepository.save(usuario);

            return ResponseEntity.ok(Map.of("Sucesso", "Conta criada com sucesso!"));

        } catch (Exception e) {

            System.out.println("Erro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Erro_Exception", e.getMessage()));
        }
    }

    /***
     * Altera o status de usuario ativo de false para true
     * 
     * @param usuario Objeto do usuário
     * @throws Exception
     */
    public void trocarStatus(Usuario usuario) throws Exception {

        try {
            
            usuario.setAtivo(true);
            usuarioRepository.save(usuario);

        } catch (Exception e) {

            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /***
     * Deleta o registro do usuário
     * 
     * @param usuario Recebe o objeto do usuário
     * @return Retorna TRUE quando deletado, FALSE quando não foi possível
     */
    public Boolean deleteUser(Usuario usuario) throws Exception{

        try {
            
            usuarioRepository.delete(usuario);

            return Boolean.TRUE;

        } catch (Exception e) {

            return Boolean.FALSE;
        }
    }
}
