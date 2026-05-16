package com.felipe.bankapi.testController;

import com.felipe.bankapi.controller.CuentaController;
import com.felipe.bankapi.exception.ResourceNotFoundException;
import com.felipe.bankapi.service.CuentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CuentaController.class)
public class CuentaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaService cuentaService;

    @Test
    void deberiaRetornar200ListarCuentas() throws Exception {

        mockMvc.perform(get("/api/cuentas"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaRetornar404CuentaNoEncontrada() throws Exception {

        when(cuentaService.obtenerCuentaPorId(99L))
                .thenThrow(new ResourceNotFoundException("Cuenta no encontrada"));

        mockMvc.perform(get("/api/cuentas/99"))
                .andExpect(status().isNotFound());
    }
}
