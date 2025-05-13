package com.pan.backend.adapters.in.controller.mapper;

import com.pan.backend.adapters.in.controller.request.EnderecoRequest;
import com.pan.backend.core.domain.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoRequestMapper {
    Endereco toDomain(EnderecoRequest request);
}
