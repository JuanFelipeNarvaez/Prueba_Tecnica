package com.felipe.bankapi.testController;

import com.felipe.bankapi.controller.ClienteController;
import com.felipe.bankapi.exception.ResourceNotFoundException;
import com.felipe.bankapi.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void deberiaRetornar200ListarClientes() throws Exception {

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaRetornar404ClienteNoEncontrado() throws Exception {
        
        when(clienteService.obtenerClientePorId(99L))
                .thenThrow(new ResourceNotFoundException("Cliente no encontrado"));

        mockMvc.perform(get("/api/clientes/99"))
                .andExpect(status().isNotFound());
    }

}
