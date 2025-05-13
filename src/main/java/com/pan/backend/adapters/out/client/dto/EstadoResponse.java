package com.pan.backend.adapters.out.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoResponse {
    private Long id;
    private String nome;
    private String sigla;
}

