package com.pan.backend.adapters.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.domain.Endereco;
import com.pan.backend.core.port.in.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Consultar cliente por CPF", description = "Retorna os dados cadastrais de um cliente", responses = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                        {
                          "cpf": "12345678900",
                          "nome": "Victor Neves",
                          "endereco": {
                            "cep": "39400000",
                            "logradouro": "Rua das Flores",
                            "complemento": "Ap 10",
                            "bairro": "Centro",
                            "cidade": "Montes Claros",
                            "estado": "MG"
                          }
                        }
                    """))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = """
                        {
                          "status": 404,
                          "error": "Cliente não encontrado",
                          "message": "Cliente com CPF 12345678900 não foi encontrado",
                          "timestamp": "2025-05-09T17:40:00Z",
                          "path": "/clientes/12345678900"
                        }
                    """)))
    })
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
