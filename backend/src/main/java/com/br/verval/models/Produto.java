package com.br.verval.models;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "produtos")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;

    @Size(max = 100, min = 1, message = "O produto deve ter entre 1 e 100 caracteres")
    @NotNull(message = "O campo não pode ser vazio")
    private String nome_produto;

    private String codigo_de_barras_produto;

    @Min(value = 0, message = "A quantidade não pode ser menor que 0")
    private int quantidade_produto;


    private Date vencimento_produto;

    @Column(updatable = false)
    private LocalDate criado_em;

    public Long getId_produto() {
        return id_produto;
    }

    public void setId_produto(Long id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getCodigo_de_barras_produto() {
        return codigo_de_barras_produto;
    }

    public void setCodigo_de_barras_produto(String codigo_de_barras_produto) {
        this.codigo_de_barras_produto = codigo_de_barras_produto;
    }

    public int getQuantidade_produto() {
        return quantidade_produto;
    }

    public void setQuantidade_produto(int quantidade_produto) {
        this.quantidade_produto = quantidade_produto;
    }

    public Date getVencimento_produto() {
        return vencimento_produto;
    }

    public void setVencimento_produto(Date vencimento_produto) {
        this.vencimento_produto = vencimento_produto;
    }

    public LocalDate getCriado_em() {
        return criado_em;
    }

    public void setCriado_em(LocalDate criado_em) {
        this.criado_em = criado_em;
    }
}
