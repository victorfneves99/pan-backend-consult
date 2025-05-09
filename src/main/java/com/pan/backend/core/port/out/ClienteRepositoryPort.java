package com.pan.backend.core.port.out;

import java.util.Optional;

import com.pan.backend.core.domain.Cliente;

public interface ClienteRepositoryPort {
    Optional<Cliente> buscarPorCpf(String cpf);
    Cliente salvar(Cliente cliente);
}