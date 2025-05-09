package com.pan.backend.adapters.in.controller;

import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.domain.Endereco;
import com.pan.backend.core.port.out.ClienteRepositoryPort;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {

    @org.springframework.boot.test.web.server.LocalServerPort
    private int port;

    @Autowired
    private ClienteRepositoryPort clienteRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        // limpa e insere cliente teste
        Cliente cliente = new Cliente(
                "12345678900",
                "Victor Neves",
                new Endereco("39400000", "Rua A", "123", "Centro", "Montes Claros", "MG"));
        clienteRepository.salvar(cliente);
    }

    @Test
    void deveConsultarClientePorCpfComSucesso() {
        given()
                .when()
                .get("/clientes/{cpf}", "12345678900")
                .then()
                .statusCode(200)
                .body("cpf", equalTo("12345678900"))
                .body("nome", equalTo("Victor Neves"))
                .body("endereco.cep", equalTo("39400000"));
    }

    @Test
    void deveRetornar404SeClienteNaoEncontrado() {
        given()
                .when()
                .get("/clientes/{cpf}", "00000000000")
                .then()
                .statusCode(404);
    }

    @Test
    void deveAtualizarEnderecoComSucesso() {
        Endereco novoEndereco = new Endereco("39404000", "Rua Nova", "Ap 22", "Centro", "Montes Claros", "MG");

        given()
                .contentType(ContentType.JSON)
                .body(novoEndereco)
                .when()
                .put("/clientes/{cpf}/endereco", "12345678900")
                .then()
                .statusCode(204);
    }

    @Test
    void deveRetornar400QuandoEnderecoForInvalido() {
        Endereco enderecoInvalido = new Endereco("", "", "", "", "", "");

        given()
                .contentType(ContentType.JSON)
                .body(enderecoInvalido)
                .when()
                .put("/clientes/{cpf}/endereco", "12345678900")
                .then()
                .statusCode(400);
    }
}
