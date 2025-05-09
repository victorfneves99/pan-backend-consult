package com.pan.backend.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.pan.backend.adapters.out.client.IbgeClient;
import com.pan.backend.adapters.out.client.ViaCepClient;
import com.pan.backend.adapters.out.client.dto.EstadoResponse;
import com.pan.backend.adapters.out.client.dto.MunicipioResponse;
import com.pan.backend.adapters.out.client.dto.ViaCepResponse;
import com.pan.backend.exception.EnderecoNotFoundException;

@SpringBootTest
class LocalidadeServiceImplTest {

    private ViaCepClient viaCepClient;
    private IbgeClient ibgeClient;
    private LocalidadeServiceImpl service;

    @BeforeEach
    void setUp() {
        viaCepClient = mock(ViaCepClient.class);
        ibgeClient = mock(IbgeClient.class);
        service = new LocalidadeServiceImpl(viaCepClient, ibgeClient);
    }

    @Test
    void deveRetornarEnderecoQuandoCepValido() {
        String cep = "39400000";
        ViaCepResponse response = new ViaCepResponse();
        response.setCep(cep);
        response.setLogradouro("Rua Exemplo");

        when(viaCepClient.buscarEnderecoPorCep(cep)).thenReturn(response);

        ViaCepResponse resultado = service.consultarCep(cep);

        assertNotNull(resultado);
        assertEquals(cep, resultado.getCep());
    }

    @Test
    void deveLancarExcecaoQuandoCepNaoEncontrado() {
        String cep = "00000000";
        when(viaCepClient.buscarEnderecoPorCep(cep)).thenReturn(null);

        assertThrows(EnderecoNotFoundException.class, () -> service.consultarCep(cep));
    }

    @Test
    void deveOrdenarEstadosComSpERjPrimeiro() {
        EstadoResponse sp = new EstadoResponse(); sp.setSigla("SP"); sp.setNome("São Paulo");
        EstadoResponse rj = new EstadoResponse(); rj.setSigla("RJ"); rj.setNome("Rio de Janeiro");
        EstadoResponse mg = new EstadoResponse(); mg.setSigla("MG"); mg.setNome("Minas Gerais");

        when(ibgeClient.buscarEstados()).thenReturn(List.of(mg, sp, rj));

        List<EstadoResponse> resultado = service.consultarEstadosOrdenados();

        assertEquals("SP", resultado.get(0).getSigla());
        assertEquals("RJ", resultado.get(1).getSigla());
    }

    @Test
    void deveRetornarListaDeMunicipiosPorEstado() {
        MunicipioResponse m1 = new MunicipioResponse(); m1.setNome("Cidade 1");
        MunicipioResponse m2 = new MunicipioResponse(); m2.setNome("Cidade 2");

        when(ibgeClient.buscarMunicipiosPorEstado(33L)).thenReturn(List.of(m1, m2));

        List<MunicipioResponse> resultado = service.consultarMunicipios(33L);

        assertEquals(2, resultado.size());
    }
}
