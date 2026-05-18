package com.felipe.bankapi.testService;

import com.felipe.bankapi.dto.request.RetiroDTO;
import com.felipe.bankapi.dto.request.TransferenciaDTO;
import com.felipe.bankapi.entity.Cuenta;
import com.felipe.bankapi.entity.enums.EstadoCuenta;
import com.felipe.bankapi.exception.BadRequestException;
import com.felipe.bankapi.repository.CuentaRepository;
import com.felipe.bankapi.repository.TransaccionRepository;
import com.felipe.bankapi.service.impl.TransaccionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransaccionServiceImplTest {
    
    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private TransaccionRepository transaccionRepository;

    @InjectMocks
    private TransaccionServiceImpl transaccionService;

    @Test
    void noDeberiaRetirarSaldoInsuficiente() {

        RetiroDTO request = RetiroDTO.builder()
                .numeroCuenta("5300000001")
                .monto(BigDecimal.valueOf(10000))
                .build();

        Cuenta cuenta = Cuenta.builder()
                .saldo(BigDecimal.valueOf(1000))
                .estado(EstadoCuenta.ACTIVA)
                .build();

        when(cuentaRepository.findByNumeroCuenta("5300000001"))
                .thenReturn(Optional.of(cuenta));

        assertThrows(BadRequestException.class,
                () -> transaccionService.retirar(request));
    }

    @Test
    void noDeberiaTransferirMismaCuenta() {

        TransferenciaDTO request = TransferenciaDTO.builder()
                .cuentaOrigen("5300000001")
                .cuentaDestino("5300000001")
                .monto(BigDecimal.valueOf(1000))
                .build();

        assertThrows(BadRequestException.class,
                () -> transaccionService.transferir(request));
    }

    @Test
    void noDeberiaPermitirCuentaInactiva() {

        RetiroDTO request = RetiroDTO.builder()
                .numeroCuenta("5300000001")
                .monto(BigDecimal.valueOf(1000))
                .build();

        Cuenta cuenta = Cuenta.builder()
                .saldo(BigDecimal.valueOf(10000))
                .estado(EstadoCuenta.INACTIVA)
                .build();

        when(cuentaRepository.findByNumeroCuenta("5300000001"))
                .thenReturn(Optional.of(cuenta));

        assertThrows(BadRequestException.class,
                () -> transaccionService.retirar(request));
    }
}
