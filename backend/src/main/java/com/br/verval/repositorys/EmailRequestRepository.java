package com.br.verval.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.verval.models.EmailRequest;

public interface EmailRequestRepository extends JpaRepository<EmailRequest, Long>{

    @Query("SELECT e FROM EmailRequest e WHERE e.email = :email")
    List<EmailRequest> findByEmail(@Param("email") String email);
} 
