package com.felipe.bankapi.service;

import com.felipe.bankapi.dto.request.CuentaRequestDTO;
import com.felipe.bankapi.dto.response.CuentaResponseDTO;
import java.util.List;

public interface CuentaService {
    CuentaResponseDTO crearCuenta(CuentaRequestDTO request);

    List<CuentaResponseDTO> listarCuentas();

    CuentaResponseDTO obtenerCuentaPorId(Long id);

    CuentaResponseDTO actualizarEstadoCuenta(Long id, String estado);

    void cancelarCuenta(Long id);
}
