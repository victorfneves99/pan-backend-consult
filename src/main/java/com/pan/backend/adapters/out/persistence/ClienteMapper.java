package com.pan.backend.adapters.out.persistence;

import org.springframework.stereotype.Component;

import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.domain.Endereco;

@Component
public class ClienteMapper {

    public Cliente toDomain(ClienteEntity entity) {
        EnderecoEmbeddable e = entity.getEndereco();
        Endereco endereco = new Endereco(
                e.getCep(), e.getLogradouro(), e.getComplemento(),
                e.getBairro(), e.getCidade(), e.getEstado());

        return new Cliente(entity.getCpf(), entity.getNome(), endereco);
    }

    public ClienteEntity toEntity(Cliente domain) {
        Endereco endereco = domain.getEndereco();
        EnderecoEmbeddable e = new EnderecoEmbeddable();
        e.setCep(endereco.getCep());
        e.setLogradouro(endereco.getLogradouro());
        e.setComplemento(endereco.getComplemento());
        e.setBairro(endereco.getBairro());
        e.setCidade(endereco.getCidade());
        e.setEstado(endereco.getEstado());

        ClienteEntity entity = new ClienteEntity();
        entity.setCpf(domain.getCpf());
        entity.setNome(domain.getNome());
        entity.setEndereco(e);
        return entity;
    }
}
