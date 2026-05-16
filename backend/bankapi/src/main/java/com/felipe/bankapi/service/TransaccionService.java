package com.felipe.bankapi.service;

import com.felipe.bankapi.dto.request.ConsignacionDTO;
import com.felipe.bankapi.dto.request.RetiroDTO;
import com.felipe.bankapi.dto.request.TransferenciaDTO;
import com.felipe.bankapi.dto.response.TransaccionResponseDTO;

public interface TransaccionService {

    TransaccionResponseDTO consignar(ConsignacionDTO request);

    TransaccionResponseDTO retirar(RetiroDTO request);

    TransaccionResponseDTO transferir(TransferenciaDTO request);
}