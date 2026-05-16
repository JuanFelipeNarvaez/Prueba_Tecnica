package com.felipe.bankapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

import com.felipe.bankapi.entity.enums.TipoIdentificacion;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequestDTO {
    @NotNull(message = "El tipo de identificación es obligatorio")
    private TipoIdentificacion tipoIdentificacion;

    @NotBlank(message = "El número de identificación es obligatorio")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "La identificación solo puede contener números"
    )
    @Size(
            min = 5,
            max = 20,
            message = "La identificación debe tener entre 5 y 20 caracteres"
    )
    private String numeroIdentificacion;

    @NotBlank(message = "El primer nombre es obligatorio")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$",
            message = "El primer nombre solo puede contener letras"
    )
    @Size(
            min = 2,
            max = 50,
            message = "El primer nombre debe tener entre 2 y 50 caracteres"
    )
    private String primerNombre;

    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$",
            message = "El segundo nombre solo puede contener letras"
    )
    @Size(
            max = 50,
            message = "El segundo nombre no puede superar 50 caracteres"
    )
    private String segundoNombre;

    @NotBlank(message = "El primer apellido es obligatorio")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$",
            message = "El primer apellido solo puede contener letras"
    )
    @Size(
            min = 2,
            max = 50,
            message = "El primer apellido debe tener entre 2 y 50 caracteres"
    )
    private String primerApellido;

    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$",
            message = "El segundo apellido solo puede contener letras"
    )
    @Size(
            max = 50,
            message = "El segundo apellido no puede superar 50 caracteres"
    )
    private String segundoApellido;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    @Size(
            max = 100,
            message = "El correo no puede superar 100 caracteres"
    )
    private String correoElectronico;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;
}
