package com.pan.backend.core.port.in;

import com.pan.backend.core.domain.Cliente;
import com.pan.backend.core.domain.Endereco;

public interface ClienteService {
    Cliente consultarPorCpf(String cpf);
    void alterarEndereco(String cpf, Endereco novoEndereco);
}
