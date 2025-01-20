package com.br.verval.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.verval.models.Usuario;
import com.br.verval.repositorys.UsuarioRepository;
import com.br.verval.utils.Util;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
  
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

            // Salva o usuário no banco de dados
            usuarioRepository.save(usuario);

            return ResponseEntity.ok(Map.of("Sucesso", "Conta criada com sucesso!"));

        } catch (Exception e) {

            System.out.println("Erro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Erro_Exception", e.getMessage()));
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
