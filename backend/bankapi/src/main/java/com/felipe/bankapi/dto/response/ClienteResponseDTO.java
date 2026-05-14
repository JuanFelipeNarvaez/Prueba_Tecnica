package com.felipe.bankapi.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponseDTO {

    private Long id;

    private String tipoIdentificacion;

    private String numeroIdentificacion;

    private String primerNombre;

    private String segundoNombre;

    private String primerApellido;

    private String segundoApellido;

    private String correoElectronico;

    private LocalDate fechaNacimiento;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaModificacion;
}
