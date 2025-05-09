package com.pan.backend.adapters.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.domain.Endereco;
import com.pan.backend.core.port.in.ClienteService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/clientes")
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> consultarCliente(@PathVariable String cpf) {
        log.info("Recebida requisição GET /clientes/{}", cpf);
        Cliente cliente = clienteService.consultarPorCpf(cpf);
        log.info("Cliente retornado: {}", cliente.getCpf());
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{cpf}/endereco")
    public ResponseEntity<Void> alterarEndereco(@PathVariable String cpf,
                                                @RequestBody @Valid Endereco novoEndereco) {
        log.info("Recebida requisição PUT /clientes/{}/endereco com payload: {}", cpf, novoEndereco);
        clienteService.alterarEndereco(cpf, novoEndereco);
        log.info("Endereço atualizado com sucesso para CPF {}", cpf);
        return ResponseEntity.noContent().build();
    }
}
