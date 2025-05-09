# 🏦 PAN Backend Consult

Este repositório contém um microserviço desenvolvido como parte de um desafio técnico proposto pelo Banco PAN. A aplicação foi construída com **Java 17 + Spring Boot 3**, seguindo os princípios da **Arquitetura Hexagonal**, com foco em **clean code**, **testabilidade** e **boas práticas de API RESTful**.

---

## ✨ Funcionalidades

- 🔍 Consultar dados cadastrais de cliente por CPF  
- ✏️ Atualizar endereço de cliente  
- 🏠 Consultar endereço por CEP (ViaCEP)  
- 🗺️ Listar estados e municípios via API do IBGE  
- ⚠️ Tratamento de erros com `ProblemDetail` (RFC 7807)  
- 🧪 Testes unitários com JUnit 5 + Mockito  
- 🧾 Documentação da API com Swagger/OpenAPI

---

## 🧱 Tecnologias

- Java 17  
- Spring Boot 3  
- Spring Web, Validation, JPA  
- H2 Database (em memória)  
- SLF4J (log estruturado)
- Swagger / Springdoc OpenAPI  
- JUnit 5 + Mockito

---

## 🚀 Como executar

```bash
git clone https://github.com/victorfneves99/pan-backend-consult.git
cd pan-backend-consult
./mvnw spring-boot:run
```

---

## 🔎 Documentação da API

Acesse o Swagger em:

```
http://localhost:8080/swagger-ui.html
```

Ou baixe o arquivo OpenAPI:

[📄 openapi-spec.yaml](./docs/openapi-spec.yaml)

---

## 🧪 Executar os testes

```bash
./mvnw test
```

---

## 📂 Estrutura do Projeto

```
├── core/                # Domínio e portas
├── application/         # Casos de uso
├── adapters/
│   ├── in/              # Controllers REST
│   └── out/             # Persistência e clients externos
├── config/              # Configurações globais (Swagger, logs, etc.)
└── exception/           # Handler e exceptions personalizadas
```

---

## 👨‍💻 Autor

Desenvolvido por **Victor Francisco Neves** — feito com 💻, ☕ e clean architecture.
