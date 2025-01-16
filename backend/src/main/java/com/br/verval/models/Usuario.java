package com.br.verval.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    @NotNull(message = "O nome é obrigatório")
    private String nome_usuario;

    @Email(message = "O e-mail deve ser válido")
    private String email_usuario;

    @Size(min = 6, max = 16, message = "A senha deve ter entre 6 e 16 caracteres")
    @NotNull(message = "A senha é obrigatória")
    private String senha_usuario;

    @Column(updatable = false)
    private Date criado_em;

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getSenha_usuario() {
        return senha_usuario;
    }

    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }

    public Date getCriado_em() {
        return criado_em;
    }

    public void setCriado_em(Date criado_em) {
        this.criado_em = criado_em;
    }

    public Date getCriado() {
        return criado_em;
    }


    public void setCriado(final Date criado_em) {
        this.criado_em = criado_em;
    }


    
}
