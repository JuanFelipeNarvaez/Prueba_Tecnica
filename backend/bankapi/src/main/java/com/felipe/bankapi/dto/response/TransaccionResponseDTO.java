package com.felipe.bankapi.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.felipe.bankapi.entity.enums.TipoTransaccion;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionResponseDTO {

    private Long id;

    private TipoTransaccion tipoTransaccion;

    private BigDecimal monto;

    private LocalDateTime fecha;

    private String cuentaOrigen;

    private String cuentaDestino;
}