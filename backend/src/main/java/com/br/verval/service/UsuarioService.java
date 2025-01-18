package com.br.verval.service;

import org.springframework.stereotype.Service;

import com.br.verval.models.Usuario;

@Service
public class UsuarioService {
  
    /***
     * Cria o registro do usuário
     * 
     * @param usuario Recebe o objeto do usuário
     * @return Retorna TRUE caso tenha criado, FALSE quando não foi possível
     */
    public Boolean createUser(Usuario usuario){

        return Boolean.FALSE;
    }

    /***
     * Deleta o registro do usuário
     * 
     * @param usuario Recebe o objeto do usuário
     * @return Retorna TRUE quando deletado, FALSE quando não foi possível
     */
    public Boolean deleteUser(Usuario usuario){

        return Boolean.FALSE;
    }
}
