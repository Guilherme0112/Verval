package com.br.verval.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.verval.models.ConfirmEmail;

public interface ConfirmEmailRepository extends JpaRepository<ConfirmEmail, Long>{

    @Query("SELECT c FROM ConfirmEmail c WHERE c.token = :token")
    List<ConfirmEmail> findByToken(@Param("token") String token);
}
