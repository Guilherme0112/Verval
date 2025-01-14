package com.br.verval.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;

@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    @NotNull(message = "O nome é obrigatório")
    private String nome;

    @Email(message = "O e-mail deve ser válido")
    private String email;

    @Size(min = 6, max = 16, message = "A senha deve ter entre 6 e 16 caracteres")
    @NotNull(message = "A senha é obrigatória")
    private String senha;

    @Column(updatable = false)
    private Date criado_em;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Date getCriado() {
        return criado_em;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCriado(Date criado_em) {
        this.criado_em = criado_em;
    }


    
}
