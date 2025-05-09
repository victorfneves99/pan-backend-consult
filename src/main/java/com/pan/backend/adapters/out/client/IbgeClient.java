package com.pan.backend.adapters.out.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pan.backend.adapters.out.client.dto.EstadoResponse;
import com.pan.backend.adapters.out.client.dto.MunicipioResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class IbgeClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("estados")
    public List<EstadoResponse> buscarEstados() {
        log.info("Buscando estados");
        String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";
        EstadoResponse[] estados = restTemplate.getForObject(url, EstadoResponse[].class);
        return Arrays.asList(estados);
    }

    @Cacheable(value = "municipios", key = "#estadoId")
    public List<MunicipioResponse> buscarMunicipiosPorEstado(Long idEstado) {
        log.info("Buscando municipios do estado {}", idEstado);
        String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/{id}/municipios";
        MunicipioResponse[] municipios = restTemplate.getForObject(url, MunicipioResponse[].class, idEstado);
        return Arrays.asList(municipios);
    }
}
