package com.felipe.bankapi.testService;

import com.felipe.bankapi.dto.request.CuentaRequestDTO;
import com.felipe.bankapi.entity.Cliente;
import com.felipe.bankapi.entity.Cuenta;
import com.felipe.bankapi.entity.enums.TipoCuenta;
import com.felipe.bankapi.exception.BadRequestException;
import com.felipe.bankapi.repository.ClienteRepository;
import com.felipe.bankapi.repository.CuentaRepository;
import com.felipe.bankapi.service.impl.CuentaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CuentaServiceImplTest {

        @Mock
        private CuentaRepository cuentaRepository;

        @Mock
        private ClienteRepository clienteRepository;

        @InjectMocks
        private CuentaServiceImpl cuentaService;

        @Test
        void deberiaCrearCuentaCorrectamente() {

                CuentaRequestDTO request = CuentaRequestDTO.builder()
                                .tipoCuenta(TipoCuenta.AHORROS)
                                .saldo(BigDecimal.valueOf(100000))
                                .clienteId(1L)
                                .exentaGmf(true)
                                .build();

                Cliente cliente = Cliente.builder()
                                .id(1L)
                                .build();

                Cuenta cuentaGuardada = Cuenta.builder()
                                .id(1L)
                                .cliente(cliente)
                                .saldo(BigDecimal.valueOf(100000))
                                .tipoCuenta(TipoCuenta.AHORROS)
                                .build();

                when(clienteRepository.findById(1L))
                                .thenReturn(Optional.of(cliente));

                when(cuentaRepository.save(any(Cuenta.class)))
                                .thenReturn(cuentaGuardada);

                cuentaService.crearCuenta(request);
        }

        @Test
        void noDeberiaCrearCuentaAhorrosSaldoNegativo() {

                CuentaRequestDTO request = CuentaRequestDTO.builder()
                                .tipoCuenta(TipoCuenta.AHORROS)
                                .saldo(BigDecimal.valueOf(-1000))
                                .clienteId(1L)
                                .build();

                when(clienteRepository.findById(1L))
                                .thenReturn(Optional.of(new Cliente()));

                assertThrows(BadRequestException.class,
                                () -> cuentaService.crearCuenta(request));
        }

        @Test
        void noDeberiaCancelarCuentaConSaldo() {

                Cuenta cuenta = Cuenta.builder()
                                .saldo(BigDecimal.valueOf(1000))
                                .build();

                when(cuentaRepository.findById(1L))
                                .thenReturn(Optional.of(cuenta));

                assertThrows(BadRequestException.class,
                                () -> cuentaService.cancelarCuenta(1L));
        }
}
