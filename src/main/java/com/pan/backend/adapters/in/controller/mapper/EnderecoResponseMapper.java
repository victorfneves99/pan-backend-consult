package com.pan.backend.adapters.in.controller.mapper;

import org.mapstruct.Mapper;

import com.pan.backend.adapters.in.controller.response.EnderecoResponse;
import com.pan.backend.core.domain.Endereco;

@Mapper(componentModel = "spring")
public interface EnderecoResponseMapper {
    EnderecoResponse toResponse(Endereco endereco);
}
