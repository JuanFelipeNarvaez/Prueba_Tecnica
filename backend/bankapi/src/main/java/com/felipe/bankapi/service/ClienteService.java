package com.felipe.bankapi.service;

import com.felipe.bankapi.dto.request.ClienteRequestDTO;
import com.felipe.bankapi.dto.response.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {

    ClienteResponseDTO crearCliente(ClienteRequestDTO request);

    List<ClienteResponseDTO> listarClientes();

    ClienteResponseDTO obtenerClientePorId(Long id);

    ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO request);

    void eliminarCliente(Long id);
}
