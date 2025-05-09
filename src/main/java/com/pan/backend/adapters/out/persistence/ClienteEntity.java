package com.pan.backend.adapters.out.persistence;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClienteEntity {
    @Id
    private String cpf;
    private String nome;

    @Embedded
    private EnderecoEmbeddable endereco;

    public ClienteEntity() {}

    // getters e setters omitidos para brevidade
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public EnderecoEmbeddable getEndereco() { return endereco; }
    public void setEndereco(EnderecoEmbeddable endereco) { this.endereco = endereco; }
}
