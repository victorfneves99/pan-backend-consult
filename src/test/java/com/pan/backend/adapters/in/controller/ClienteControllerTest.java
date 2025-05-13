package com.pan.backend.adapters.in.controller;

import com.pan.backend.adapters.in.controller.mapper.ClienteResponseMapper;
import com.pan.backend.adapters.in.controller.mapper.EnderecoRequestMapper;
import com.pan.backend.adapters.in.controller.request.EnderecoRequest;
import com.pan.backend.adapters.in.controller.response.ClienteResponse;
import com.pan.backend.adapters.in.controller.response.EnderecoResponse;
import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.domain.Endereco;
import com.pan.backend.core.port.in.ClienteService;
import com.pan.backend.exception.ClienteNotFoundException;
import com.pan.backend.exception.GlobalExceptionHandler;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClienteControllerTest {

    private ClienteService clienteService;
    private ClienteResponseMapper clienteResponseMapper;
    private EnderecoRequestMapper enderecoRequestMapper;
    private ClienteController clienteController;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        clienteService = mock(ClienteService.class);
        clienteResponseMapper = mock(ClienteResponseMapper.class);
        enderecoRequestMapper = mock(EnderecoRequestMapper.class);
        clienteController = new ClienteController(clienteService, clienteResponseMapper, enderecoRequestMapper);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void deveRetornarClienteQuandoCpfExistir() {
        // Arrange
        String cpf = "12345678900";
        Cliente cliente = new Cliente(cpf, "Victor Neves",
                new Endereco("39400000", "Rua das Flores", "Ap 10", "Centro", "Montes Claros", "MG"));
        ClienteResponse response = new ClienteResponse(cpf, "Victor Neves",
                new EnderecoResponse("39400000", "Rua das Flores", "Ap 10", "Centro", "Montes Claros", "MG"));

        when(clienteService.consultarPorCpf(cpf)).thenReturn(cliente);
        when(clienteResponseMapper.toResponse(cliente)).thenReturn(response);

        // Act + Assert
        RestAssuredMockMvc.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/v1/clientes/{cpf}", cpf)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("cpf", org.hamcrest.Matchers.equalTo(cpf))
                .body("nome", org.hamcrest.Matchers.equalTo("Victor Neves"))
                .body("endereco.cep", org.hamcrest.Matchers.equalTo("39400000"));
    }

    @Test
    void deveAtualizarEnderecoComSucesso() {
        // Arrange
        String cpf = "12345678900";
        EnderecoRequest request = new EnderecoRequest("39400000", "Rua das Flores", "Ap 10", "Centro", "Montes Claros",
                "MG");
        Endereco endereco = new Endereco("39400000", "Rua das Flores", "Ap 10", "Centro", "Montes Claros", "MG");

        when(enderecoRequestMapper.toDomain(request)).thenReturn(endereco);
        doNothing().when(clienteService).alterarEndereco(cpf, endereco);

        // Act + Assert
        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/v1/clientes/{cpf}/endereco", cpf)
                .then()
                .statusCode(204);
    }

    @Test
    void deveRetornar404ComProblemDetailQuandoClienteNaoEncontrado() {
        String cpf = "99999999999";
        when(clienteService.consultarPorCpf(cpf))
                .thenThrow(new ClienteNotFoundException(cpf));

        RestAssuredMockMvc.given()
                .accept(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .when()
                .get("/v1/clientes/{cpf}", cpf)
                .then()
                .statusCode(404)
                .body("title", equalTo("Cliente não encontrado"))
                .body("detail", equalTo("Cliente com CPF " + cpf + " não encontrado."))
                .body("status", equalTo(404))
                .body("error_category", equalTo("Generic"))
                .body("service", notNullValue());
    }

}
