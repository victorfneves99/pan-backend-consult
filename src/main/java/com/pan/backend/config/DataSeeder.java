package com.pan.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.domain.Endereco;
import com.pan.backend.core.port.out.ClienteRepositoryPort;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ClienteRepositoryPort clienteRepository;

    public DataSeeder(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {
        Endereco endereco1 = new Endereco("39400000", "Rua das Flores", "Ap 101", "Centro", "Montes Claros", "MG");
        Endereco endereco2 = new Endereco("30140071", "Av. Brasil", "Casa", "Santos Dumont", "Belo Horizonte", "MG");

        Cliente cliente1 = new Cliente("12345678900", "Victor Neves", endereco1);
        Cliente cliente2 = new Cliente("98765432100", "Matheus Neves", endereco2);

        clienteRepository.salvar(cliente1);
        clienteRepository.salvar(cliente2);
    }
}