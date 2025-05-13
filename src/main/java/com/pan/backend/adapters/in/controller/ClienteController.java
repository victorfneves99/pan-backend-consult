package com.pan.backend.adapters.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pan.backend.adapters.in.controller.mapper.ClienteResponseMapper;
import com.pan.backend.adapters.in.controller.mapper.EnderecoRequestMapper;
import com.pan.backend.adapters.in.controller.request.EnderecoRequest;
import com.pan.backend.adapters.in.controller.response.ClienteResponse;
import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.port.in.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/clientes")
@Slf4j
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteResponseMapper clienteMapper;
    private final EnderecoRequestMapper enderecoRequestMapper;


    @Operation(summary = "Consultar cliente por CPF", description = "Retorna os dados cadastrais de um cliente", responses = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class))),
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
    public ResponseEntity<ClienteResponse> consultarCliente(@PathVariable String cpf) {
        log.info("Recebida requisição GET /clientes/{}", cpf);
        Cliente cliente = clienteService.consultarPorCpf(cpf);
        ClienteResponse response = clienteMapper.toResponse(cliente);
        log.info("Cliente retornado: {}", cliente.getCpf());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{cpf}/endereco")
    public ResponseEntity<Void> alterarEndereco(@PathVariable String cpf,
            @RequestBody @Valid EnderecoRequest novoEndereco) {
        log.info("Recebida requisição PUT /clientes/{}/endereco com payload: {}", cpf, novoEndereco);
        var endereco = enderecoRequestMapper.toDomain(novoEndereco);
        clienteService.alterarEndereco(cpf,endereco);
        log.info("Endereço atualizado com sucesso para CPF {}", cpf);
        return ResponseEntity.noContent().build();
    }
}
