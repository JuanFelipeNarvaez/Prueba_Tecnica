package com.felipe.bankapi.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.felipe.bankapi.entity.enums.EstadoCuenta;
import com.felipe.bankapi.entity.enums.TipoCuenta;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaResponseDTO {
    private Long id;

    private TipoCuenta tipoCuenta;

    private String numeroCuenta;

    private EstadoCuenta estado;

    private BigDecimal saldo;

    private Boolean exentaGmf;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaModificacion;

    private Long clienteId;
}
