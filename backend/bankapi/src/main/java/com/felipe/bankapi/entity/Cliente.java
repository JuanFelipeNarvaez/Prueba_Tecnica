package com.felipe.bankapi.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoIdentificacion;

    @Column(nullable = false, unique = true)
    private String numeroIdentificacion;

    @Column(nullable = false)
    private String primerNombre;

    private String segundoNombre;

    @Column(nullable = false)
    private String primerApellido;

    private String segundoApellido;

    @Column(nullable = false, unique = true)
    private String correoElectronico;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaModificacion;
}
