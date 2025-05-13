package com.pan.backend.adapters.in.controller.mapper;

import org.mapstruct.Mapper;

import com.pan.backend.adapters.in.controller.response.ClienteResponse;
import com.pan.backend.core.domain.Cliente;

@Mapper(componentModel = "spring", uses = EnderecoResponseMapper.class)
public interface ClienteResponseMapper {
    ClienteResponse toResponse(Cliente cliente);
}
