package com.felipe.bankapi.testService;

import com.felipe.bankapi.dto.request.ClienteRequestDTO;
import com.felipe.bankapi.dto.response.ClienteResponseDTO;
import com.felipe.bankapi.entity.Cliente;
import com.felipe.bankapi.exception.BadRequestException;
import com.felipe.bankapi.repository.ClienteRepository;
import com.felipe.bankapi.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void deberiaCrearClienteCorrectamente() {

        ClienteRequestDTO request = ClienteRequestDTO.builder()
                .numeroIdentificacion("123")
                .primerNombre("Felipe")
                .primerApellido("Lopez")
                .correoElectronico("felipe@gmail.com")
                .fechaNacimiento(LocalDate.of(2000, 1, 1))
                .build();

        Cliente cliente = Cliente.builder()
                .id(1L)
                .primerNombre("Felipe")
                .build();

        when(clienteRepository.findByNumeroIdentificacion("123"))
                .thenReturn(Optional.empty());

        when(clienteRepository.findByCorreoElectronico("felipe@gmail.com"))
                .thenReturn(Optional.empty());

        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(cliente);

        ClienteResponseDTO response = clienteService.crearCliente(request);

        assertNotNull(response);
        assertEquals("Felipe", response.getPrimerNombre());
    }

    @Test
    void noDeberiaCrearClienteMenorEdad() {

        ClienteRequestDTO request = ClienteRequestDTO.builder()
                .fechaNacimiento(LocalDate.now().minusYears(15))
                .build();

        assertThrows(BadRequestException.class,
                () -> clienteService.crearCliente(request));
    }

    @Test
    void noDeberiaCrearClienteConCorreoDuplicado() {

        ClienteRequestDTO request = ClienteRequestDTO.builder()
                .numeroIdentificacion("123")
                .correoElectronico("felipe@gmail.com")
                .fechaNacimiento(LocalDate.of(2000, 1, 1))
                .build();

        when(clienteRepository.findByNumeroIdentificacion("123"))
                .thenReturn(Optional.empty());

        when(clienteRepository.findByCorreoElectronico("felipe@gmail.com"))
                .thenReturn(Optional.of(new Cliente()));

        assertThrows(BadRequestException.class,
                () -> clienteService.crearCliente(request));
    }
}
