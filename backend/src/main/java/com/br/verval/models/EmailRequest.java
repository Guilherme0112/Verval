package com.br.verval.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_requests")
public class EmailRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    

    private String email;

    @Column(updatable = false)
    private LocalDateTime requirido_em = LocalDateTime.now();
    
    public EmailRequest(String email){
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRequirido_em() {
        return requirido_em;
    }

    public void setRequirido_em(LocalDateTime requirido_em) {
        this.requirido_em = requirido_em;
    }
}
