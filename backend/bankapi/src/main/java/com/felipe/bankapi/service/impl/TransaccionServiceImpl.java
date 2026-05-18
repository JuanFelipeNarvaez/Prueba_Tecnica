package com.felipe.bankapi.service.impl;

import com.felipe.bankapi.entity.enums.*;
import com.felipe.bankapi.dto.request.ConsignacionDTO;
import com.felipe.bankapi.dto.request.RetiroDTO;
import com.felipe.bankapi.dto.request.TransferenciaDTO;
import com.felipe.bankapi.dto.response.TransaccionResponseDTO;
import com.felipe.bankapi.entity.Cuenta;
import com.felipe.bankapi.entity.Transaccion;
import com.felipe.bankapi.exception.BadRequestException;
import com.felipe.bankapi.exception.ResourceNotFoundException;
import com.felipe.bankapi.repository.CuentaRepository;
import com.felipe.bankapi.repository.TransaccionRepository;
import com.felipe.bankapi.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService{
    
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;

    @Override
    @Transactional
    public TransaccionResponseDTO consignar(ConsignacionDTO request) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(request.getNumeroCuenta())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));

        validarCuentaActiva(cuenta);

        cuenta.setSaldo(cuenta.getSaldo().add(request.getMonto()));

        cuentaRepository.save(cuenta);

        Transaccion transaccion = Transaccion.builder()
                .tipoTransaccion(TipoTransaccion.CONSIGNACION)
                .monto(request.getMonto())
                .cuentaDestino(cuenta)
                .build();

        Transaccion transaccionGuardada = transaccionRepository.save(transaccion);

        return mapToResponse(transaccionGuardada);
    }

    @Override
    @Transactional
    public TransaccionResponseDTO retirar(RetiroDTO request) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(request.getNumeroCuenta())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));

        validarCuentaActiva(cuenta);

        if (cuenta.getSaldo().compareTo(request.getMonto()) < 0) {
            throw new BadRequestException("Saldo insuficiente");
        }

        cuenta.setSaldo(cuenta.getSaldo().subtract(request.getMonto()));

        cuentaRepository.save(cuenta);

        Transaccion transaccion = Transaccion.builder()
                .tipoTransaccion(TipoTransaccion.RETIRO)
                .monto(request.getMonto())
                .cuentaOrigen(cuenta)
                .build();

        Transaccion transaccionGuardada = transaccionRepository.save(transaccion);

        return mapToResponse(transaccionGuardada);
    }

    @Override
    @Transactional
    public TransaccionResponseDTO transferir(TransferenciaDTO request) {

        if (request.getCuentaOrigen().equals(request.getCuentaDestino())) {
            throw new BadRequestException(
                    "La cuenta origen y destino no pueden ser iguales"
            );
        }

        Cuenta cuentaOrigen = cuentaRepository.findByNumeroCuenta(request.getCuentaOrigen())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta origen no encontrada"));

        Cuenta cuentaDestino = cuentaRepository.findByNumeroCuenta(request.getCuentaDestino())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta destino no encontrada"));

        validarCuentaActiva(cuentaOrigen);
        validarCuentaActiva(cuentaDestino);

        if (cuentaOrigen.getSaldo().compareTo(request.getMonto()) < 0) {
            throw new BadRequestException("Saldo insuficiente");
        }

        cuentaOrigen.setSaldo(
                cuentaOrigen.getSaldo().subtract(request.getMonto())
        );

        cuentaDestino.setSaldo(
                cuentaDestino.getSaldo().add(request.getMonto())
        );

        cuentaRepository.save(cuentaOrigen);
        cuentaRepository.save(cuentaDestino);

        Transaccion transaccion = Transaccion.builder()
                .tipoTransaccion(TipoTransaccion.TRANSFERENCIA)
                .monto(request.getMonto())
                .cuentaOrigen(cuentaOrigen)
                .cuentaDestino(cuentaDestino)
                .build();

        Transaccion transaccionGuardada = transaccionRepository.save(transaccion);

        return mapToResponse(transaccionGuardada);
    }
    private void validarCuentaActiva(Cuenta cuenta) {

        if (cuenta.getEstado() != EstadoCuenta.ACTIVA) {
            throw new BadRequestException(
                    "La cuenta debe estar activa para realizar transacciones"
            );
        }
    }

    private TransaccionResponseDTO mapToResponse(Transaccion transaccion) {

        return TransaccionResponseDTO.builder()
                .id(transaccion.getId())
                .tipoTransaccion(transaccion.getTipoTransaccion())
                .monto(transaccion.getMonto())
                .fecha(transaccion.getFecha())
                .cuentaOrigen(
                        transaccion.getCuentaOrigen() != null
                                ? transaccion.getCuentaOrigen().getNumeroCuenta()
                                : null
                )
                .cuentaDestino(
                        transaccion.getCuentaDestino() != null
                                ? transaccion.getCuentaDestino().getNumeroCuenta()
                                : null
                )
                .build();
    }
}
