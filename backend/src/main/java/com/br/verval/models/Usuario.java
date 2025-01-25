package com.br.verval.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message =  "O e-mail é obrigatório")
    private String email_usuario;

    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @NotNull(message = "A senha é obrigatória")
    private String senha_usuario;

    private Boolean ativo_usuario = false;

    @Column(updatable = false)
    private LocalDate criado_em = LocalDate.now();

    public Long getId() {
        return id_usuario;
    }

    public void setId(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome() {
        return nome_usuario;
    }

    public void setNome(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEmail() {
        return email_usuario;
    }

    public void setEmail(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getSenha() {
        return senha_usuario;
    }

    public Boolean getAtivo() {
        return ativo_usuario;
    }

    public void setAtivo(Boolean ativo_usuario) {
        this.ativo_usuario = ativo_usuario;
    }

    public void setSenha(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }

    public LocalDate getCriado() {
        return criado_em;
    }

    public void setCriado(LocalDate criado_em) {
        this.criado_em = criado_em;
    }

}
