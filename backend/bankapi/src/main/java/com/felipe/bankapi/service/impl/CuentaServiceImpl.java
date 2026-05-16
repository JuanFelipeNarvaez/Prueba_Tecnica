package com.felipe.bankapi.service.impl;

import com.felipe.bankapi.dto.request.CuentaRequestDTO;
import com.felipe.bankapi.dto.response.CuentaResponseDTO;
import com.felipe.bankapi.entity.Cliente;
import com.felipe.bankapi.entity.Cuenta;
import com.felipe.bankapi.entity.enums.EstadoCuenta;
import com.felipe.bankapi.entity.enums.TipoCuenta;
import com.felipe.bankapi.exception.BadRequestException;
import com.felipe.bankapi.exception.ResourceNotFoundException;
import com.felipe.bankapi.repository.ClienteRepository;
import com.felipe.bankapi.repository.CuentaRepository;
import com.felipe.bankapi.service.CuentaService;
import com.felipe.bankapi.util.GeneradorCuentaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public CuentaResponseDTO crearCuenta(CuentaRequestDTO request) {

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        if (request.getTipoCuenta() == TipoCuenta.AHORROS &&
                request.getSaldo().compareTo(BigDecimal.ZERO) < 0) {

            throw new BadRequestException("La cuenta de ahorros no puede tener saldo negativo");
        }

        String numeroCuenta = GeneradorCuentaUtil.generarNumeroCuenta(request.getTipoCuenta());

        Cuenta cuenta = Cuenta.builder()
                .tipoCuenta(request.getTipoCuenta())
                .numeroCuenta(numeroCuenta)
                .estado(EstadoCuenta.ACTIVA)
                .saldo(request.getSaldo())
                .exentaGmf(request.getExentaGmf())
                .cliente(cliente)
                .build();

        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        return mapToResponse(cuentaGuardada);
    }

    @Override
    public List<CuentaResponseDTO> listarCuentas() {

        return cuentaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CuentaResponseDTO obtenerCuentaPorId(Long id) {

        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));

        return mapToResponse(cuenta);
    }

    @Override
    public CuentaResponseDTO actualizarEstadoCuenta(Long id, String estado) {

        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));

        cuenta.setEstado(EstadoCuenta.valueOf(estado.toUpperCase()));

        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

        return mapToResponse(cuentaActualizada);
    }

    @Override
    public void cancelarCuenta(Long id) {

        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));

        if (cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
            throw new BadRequestException(
                    "No se puede cancelar una cuenta con saldo mayor a 0"
            );
        }

        cuenta.setEstado(EstadoCuenta.CANCELADA);

        cuentaRepository.save(cuenta);
    }
    private CuentaResponseDTO mapToResponse(Cuenta cuenta) {

        return CuentaResponseDTO.builder()
                .id(cuenta.getId())
                .tipoCuenta(cuenta.getTipoCuenta())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .estado(cuenta.getEstado())
                .saldo(cuenta.getSaldo())
                .exentaGmf(cuenta.getExentaGmf())
                .fechaCreacion(cuenta.getFechaCreacion())
                .fechaModificacion(cuenta.getFechaModificacion())
                .clienteId(cuenta.getCliente().getId())
                .build();
    }
}
