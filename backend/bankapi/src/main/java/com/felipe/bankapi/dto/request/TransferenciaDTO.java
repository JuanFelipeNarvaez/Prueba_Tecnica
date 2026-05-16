package com.felipe.bankapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferenciaDTO {

    @NotNull(message = "La cuenta origen es obligatoria")
    private String cuentaOrigen;

    @NotNull(message = "La cuenta destino es obligatoria")
    private String cuentaDestino;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "El monto debe ser mayor a 0")
    private BigDecimal monto;
}
