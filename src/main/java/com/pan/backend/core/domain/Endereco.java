package com.pan.backend.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class Endereco {
    @NotBlank
    @Schema(example = "39400000", description = "CEP do cliente")
    private String cep;
    @NotBlank
    @Schema(example = "Rua das Flores")
    private String logradouro;
    @NotBlank
    @Schema(example = "Ap 101")
    private String complemento;
    @NotBlank
    @Schema(example = "Centro")
    private String bairro;
    @NotBlank
    @Schema(example = "Montes Claros")
    private String cidade;
    @NotBlank
    @Schema(example = "MG")
    private String estado;

    public Endereco() {}

    public Endereco(String cep, String logradouro, String complemento,
                    String bairro, String cidade, String estado) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    // getters e setters omitidos para brevidade
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
