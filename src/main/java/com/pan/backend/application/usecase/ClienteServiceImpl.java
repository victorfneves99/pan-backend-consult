package com.pan.backend.application.usecase;

import org.springframework.stereotype.Service;

import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.domain.Endereco;
import com.pan.backend.core.port.in.ClienteService;
import com.pan.backend.core.port.out.ClienteRepositoryPort;
import com.pan.backend.exception.ClienteNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepositoryPort clienteRepository;

    public ClienteServiceImpl(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente consultarPorCpf(String cpf) {
        log.info("Buscando cliente com CPF: {}", cpf);
        return clienteRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> {
                    log.warn("Cliente com CPF {} nao encontrado.", cpf);
                    return new ClienteNotFoundException(cpf);
                });
    }

    @Override
    public void alterarEndereco(String cpf, Endereco novoEndereco) {
        log.info("Alterando endereço do cliente com CPF: {}", cpf);
        Cliente cliente = consultarPorCpf(cpf);
        cliente.setEndereco(novoEndereco);
        clienteRepository.salvar(cliente);
    }
}
