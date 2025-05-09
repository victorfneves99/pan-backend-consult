package com.pan.backend.exception;

public class EnderecoNotFoundException extends RuntimeException {
    public EnderecoNotFoundException(String cep) {
        super("Endereço não encontrado para o CEP: " + cep);
    }
}