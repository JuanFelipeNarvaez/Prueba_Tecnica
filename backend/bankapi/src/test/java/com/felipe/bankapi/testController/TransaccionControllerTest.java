package com.felipe.bankapi.testController;

import com.felipe.bankapi.controller.TransaccionController;
import com.felipe.bankapi.service.TransaccionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransaccionController.class)
public class TransaccionControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransaccionService transaccionService;

    @Test
    void deberiaRetornar400ConsignacionInvalida() throws Exception {

        mockMvc.perform(post("/api/transacciones/consignar"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deberiaRetornar400TransferenciaInvalida() throws Exception {

        mockMvc.perform(post("/api/transacciones/transferir"))
                .andExpect(status().isBadRequest());
    }
}
