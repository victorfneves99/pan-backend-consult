package com.pan.backend.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.domain.Endereco;
import com.pan.backend.core.port.out.ClienteRepositoryPort;
import com.pan.backend.exception.ClienteNotFoundException;

class ClienteServiceImplTest {

    private ClienteRepositoryPort clienteRepository;
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        clienteRepository = mock(ClienteRepositoryPort.class);
        clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @Test
    void deveRetornarClienteQuandoCpfValido() {
        // given
        String cpf = "12345678900";
        Cliente cliente = new Cliente(cpf, "Victor", new Endereco());
        when(clienteRepository.buscarPorCpf(cpf)).thenReturn(Optional.of(cliente));

        // when
        Cliente resultado = clienteService.consultarPorCpf(cpf);

        // then
        assertNotNull(resultado);
        assertEquals("Victor", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        String cpf = "00000000000";
        when(clienteRepository.buscarPorCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> clienteService.consultarPorCpf(cpf));
    }

    @Test
    void deveAlterarEnderecoDoCliente() {
        // given
        String cpf = "12345678900";
        Endereco novoEndereco = new Endereco("39400-000", "Rua Nova", "123", "Centro", "Montes Claros", "MG");
        Cliente cliente = new Cliente(cpf, "Victor", new Endereco());

        when(clienteRepository.buscarPorCpf(cpf)).thenReturn(Optional.of(cliente));
        when(clienteRepository.salvar(any())).thenReturn(cliente);

        // when
        clienteService.alterarEndereco(cpf, novoEndereco);

        // then
        verify(clienteRepository).salvar(argThat(c -> c.getEndereco().getCep().equals("39400-000")));
    }
}
