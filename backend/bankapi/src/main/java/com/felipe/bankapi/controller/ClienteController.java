package com.felipe.bankapi.controller;

import com.felipe.bankapi.dto.request.ClienteRequestDTO;
import com.felipe.bankapi.dto.response.ClienteResponseDTO;
import com.felipe.bankapi.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(
            @Valid @RequestBody ClienteRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.crearCliente(request));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {

        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorId(@PathVariable Long id) {

        return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO request) {

        return ResponseEntity.ok(clienteService.actualizarCliente(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {

        clienteService.eliminarCliente(id);

        return ResponseEntity.noContent().build();
    }
}
