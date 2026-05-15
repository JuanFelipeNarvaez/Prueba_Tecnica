package com.felipe.bankapi.controller;

import com.felipe.bankapi.dto.request.CuentaRequestDTO;
import com.felipe.bankapi.dto.response.CuentaResponseDTO;
import com.felipe.bankapi.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaResponseDTO> crearCuenta(
            @Valid @RequestBody CuentaRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cuentaService.crearCuenta(request));
    }

    @GetMapping
    public ResponseEntity<List<CuentaResponseDTO>> listarCuentas() {

        return ResponseEntity.ok(cuentaService.listarCuentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> obtenerCuentaPorId(@PathVariable Long id) {

        return ResponseEntity.ok(cuentaService.obtenerCuentaPorId(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<CuentaResponseDTO> actualizarEstadoCuenta(
            @PathVariable Long id,
            @RequestParam String estado) {

        return ResponseEntity.ok(
                cuentaService.actualizarEstadoCuenta(id, estado)
        );
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelarCuenta(@PathVariable Long id) {

        cuentaService.cancelarCuenta(id);

        return ResponseEntity.ok("Cuenta cancelada correctamente");
    }
}
