package com.felipe.bankapi.service.impl;

import com.felipe.bankapi.dto.request.ClienteRequestDTO;
import com.felipe.bankapi.dto.response.ClienteResponseDTO;
import com.felipe.bankapi.entity.Cliente;
import com.felipe.bankapi.exception.BadRequestException;
import com.felipe.bankapi.exception.ResourceNotFoundException;
import com.felipe.bankapi.repository.ClienteRepository;
import com.felipe.bankapi.service.ClienteService;
import com.felipe.bankapi.util.EdadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    
    private final ClienteRepository clienteRepository;

    @Override
    public ClienteResponseDTO crearCliente(ClienteRequestDTO request) {

        if (!EdadUtil.esMayorDeEdad(request.getFechaNacimiento())) {
            throw new BadRequestException("El cliente debe ser mayor de edad");
        }

        clienteRepository.findByNumeroIdentificacion(request.getNumeroIdentificacion())
                .ifPresent(cliente -> {
                    throw new BadRequestException("Ya existe un cliente con esa identificación");
                });

        clienteRepository.findByCorreoElectronico(request.getCorreoElectronico())
                .ifPresent(cliente -> {
                    throw new BadRequestException("Ya existe un cliente con ese correo electrónico");
                });

        Cliente cliente = Cliente.builder()
                .tipoIdentificacion(request.getTipoIdentificacion())
                .numeroIdentificacion(request.getNumeroIdentificacion())
                .primerNombre(request.getPrimerNombre())
                .segundoNombre(request.getSegundoNombre())
                .primerApellido(request.getPrimerApellido())
                .segundoApellido(request.getSegundoApellido())
                .correoElectronico(request.getCorreoElectronico())
                .fechaNacimiento(request.getFechaNacimiento())
                .build();

        Cliente clienteGuardado = clienteRepository.save(cliente);

        return mapToResponse(clienteGuardado);
    }

    @Override
    public List<ClienteResponseDTO> listarClientes() {

        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ClienteResponseDTO obtenerClientePorId(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        return mapToResponse(cliente);
    }

    @Override
    public ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO request) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        if (!EdadUtil.esMayorDeEdad(request.getFechaNacimiento())) {
            throw new BadRequestException("El cliente debe ser mayor de edad");
        }

        cliente.setTipoIdentificacion(request.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(request.getNumeroIdentificacion());
        cliente.setPrimerNombre(request.getPrimerNombre());
        cliente.setSegundoNombre(request.getSegundoNombre());
        cliente.setPrimerApellido(request.getPrimerApellido());
        cliente.setSegundoApellido(request.getSegundoApellido());
        cliente.setCorreoElectronico(request.getCorreoElectronico());
        cliente.setFechaNacimiento(request.getFechaNacimiento());

        Cliente clienteActualizado = clienteRepository.save(cliente);

        return mapToResponse(clienteActualizado);
    }

    @Override
    public void eliminarCliente(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        clienteRepository.delete(cliente);
    }

    private ClienteResponseDTO mapToResponse(Cliente cliente) {

        return ClienteResponseDTO.builder()
                .id(cliente.getId())
                .tipoIdentificacion(cliente.getTipoIdentificacion())
                .numeroIdentificacion(cliente.getNumeroIdentificacion())
                .primerNombre(cliente.getPrimerNombre())
                .segundoNombre(cliente.getSegundoNombre())
                .primerApellido(cliente.getPrimerApellido())
                .segundoApellido(cliente.getSegundoApellido())
                .correoElectronico(cliente.getCorreoElectronico())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .fechaCreacion(cliente.getFechaCreacion())
                .fechaModificacion(cliente.getFechaModificacion())
                .build();
    }
}
