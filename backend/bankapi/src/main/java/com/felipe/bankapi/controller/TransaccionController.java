package com.felipe.bankapi.controller;

import com.felipe.bankapi.dto.request.ConsignacionDTO;
import com.felipe.bankapi.dto.request.RetiroDTO;
import com.felipe.bankapi.dto.request.TransferenciaDTO;
import com.felipe.bankapi.dto.response.TransaccionResponseDTO;
import com.felipe.bankapi.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    @PostMapping("/consignar")
    public ResponseEntity<TransaccionResponseDTO> consignar(
            @Valid @RequestBody ConsignacionDTO request) {

        return ResponseEntity.ok(
                transaccionService.consignar(request)
        );
    }

    @PostMapping("/retirar")
    public ResponseEntity<TransaccionResponseDTO> retirar(
            @Valid @RequestBody RetiroDTO request) {

        return ResponseEntity.ok(
                transaccionService.retirar(request)
        );
    }

    @PostMapping("/transferir")
    public ResponseEntity<TransaccionResponseDTO> transferir(
            @Valid @RequestBody TransferenciaDTO request) {

        return ResponseEntity.ok(
                transaccionService.transferir(request)
        );
    }
}