package com.br.verval.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.verval.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    @Query("SELECT u FROM Usuario u WHERE u.email_usuario = :email AND u.ativo_usuario = :ativo")
    public List<Usuario> findByEmail(@Param("email") String email, 
                                     @Param("ativo") Boolean ativo);
}
