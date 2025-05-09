package com.pan.backend.adapters.in.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pan.backend.adapters.out.client.dto.EstadoResponse;
import com.pan.backend.adapters.out.client.dto.MunicipioResponse;
import com.pan.backend.adapters.out.client.dto.ViaCepResponse;
import com.pan.backend.application.usecase.LocalidadeServiceImpl;

@RestController
@RequestMapping("/localidades")
public class LocalidadeController {

    private final LocalidadeServiceImpl localidadeService;

    public LocalidadeController(LocalidadeServiceImpl localidadeService) {
        this.localidadeService = localidadeService;
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<ViaCepResponse> buscarPorCep(@PathVariable String cep) {
        return ResponseEntity.ok(localidadeService.consultarCep(cep));
    }

    @GetMapping("/estados")
    public ResponseEntity<List<EstadoResponse>> buscarEstados() {
        return ResponseEntity.ok(localidadeService.consultarEstadosOrdenados());
    }

    @GetMapping("/estados/{id}/municipios")
    public ResponseEntity<List<MunicipioResponse>> buscarMunicipios(@PathVariable Long id) {
        return ResponseEntity.ok(localidadeService.consultarMunicipios(id));
    }
}
