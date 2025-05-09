package com.pan.backend.adapters.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.port.out.ClienteRepositoryPort;

@Repository
public class ClienteRepositoryImpl implements ClienteRepositoryPort {

    private final ClienteJpaRepository jpaRepository;
    private final ClienteMapper mapper;

    public ClienteRepositoryImpl(ClienteJpaRepository jpaRepository, ClienteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return jpaRepository.findById(cpf).map(mapper::toDomain);
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
