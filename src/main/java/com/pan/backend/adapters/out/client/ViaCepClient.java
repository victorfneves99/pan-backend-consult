package com.pan.backend.adapters.out.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.pan.backend.adapters.out.client.dto.ViaCepResponse;
import com.pan.backend.exception.EnderecoNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ViaCepClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "https://viacep.com.br/ws/{cep}/json";

    public ViaCepResponse buscarEnderecoPorCep(String cep) {
        try {
            String url = URL.replace("{cep}", cep); // "https://viacep.com.br/ws/" + cep + "/json";
            return restTemplate.getForObject(url, ViaCepResponse.class);
        } catch (HttpClientErrorException e) {
            log.warn("CEP inválido informado: {}", cep);
            throw new EnderecoNotFoundException(cep);
        } catch (Exception e) {
            log.error("Erro ao consultar CEP: {}", cep, e);
            throw new RuntimeException("Erro ao consultar o endereço via ViaCEP");
        }
    }
}
