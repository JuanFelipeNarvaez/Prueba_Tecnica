package com.felipe.bankapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

import com.felipe.bankapi.entity.enums.TipoCuenta;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaRequestDTO {
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El saldo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "El saldo no puede ser negativo")
    private BigDecimal saldo;

    @NotNull(message = "Debe indicar si la cuenta es exenta GMF")
    private Boolean exentaGmf;

    @NotNull(message = "El id del cliente es obligatorio")
    private Long clienteId;
}
