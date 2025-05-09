package com.pan.backend.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pan.backend.adapters.out.client.IbgeClient;
import com.pan.backend.adapters.out.client.ViaCepClient;
import com.pan.backend.adapters.out.client.dto.EstadoResponse;
import com.pan.backend.adapters.out.client.dto.MunicipioResponse;
import com.pan.backend.adapters.out.client.dto.ViaCepResponse;
import com.pan.backend.exception.EnderecoNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LocalidadeServiceImpl {

    private final ViaCepClient viaCepClient;
    private final IbgeClient ibgeClient;

    public LocalidadeServiceImpl(ViaCepClient viaCepClient, IbgeClient ibgeClient) {
        this.viaCepClient = viaCepClient;
        this.ibgeClient = ibgeClient;
    }

    public ViaCepResponse consultarCep(String cep) {
        ViaCepResponse response = viaCepClient.buscarEnderecoPorCep(cep);
        log.info("CEP consultado: {}", cep);

        if (response == null || response.getCep() == null) {
            log.warn("CEP inválido informado: {}", cep);
            throw new EnderecoNotFoundException(cep);
        }

        return response;
    }

    public List<EstadoResponse> consultarEstadosOrdenados() {
        List<EstadoResponse> estados = ibgeClient.buscarEstados();
        return estados.stream()
                .sorted((a, b) -> {
                    if (a.getSigla().equals("SP"))
                        return -1;
                    if (b.getSigla().equals("SP"))
                        return 1;
                    if (a.getSigla().equals("RJ"))
                        return -1;
                    if (b.getSigla().equals("RJ"))
                        return 1;
                    return a.getNome().compareTo(b.getNome());
                })
                .collect(Collectors.toList());
    }

    public List<MunicipioResponse> consultarMunicipios(Long idEstado) {
        return ibgeClient.buscarMunicipiosPorEstado(idEstado);
    }
}
