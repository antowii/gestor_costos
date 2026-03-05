package com.antonella.gestor_costos.controller;

import com.antonella.gestor_costos.service.RegistroCompras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;

@WebMvcTest(ProductosController.class)
public class ProductosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistroCompras registroCompras;

    @Test
    void deberiaRetornarStatus200AlConsultarCompras() throws Exception {
        mockMvc.perform(get("http://localhost:8080/compras")).andExpect(status().isOk());
    }

    @Test
    void deberiaRetornar400CuandoPrecioEsNegativo() throws Exception {
        mockMvc.perform(post("/compras")
                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                         {
                                             "nombre": "Harina",
                                             "precioTotal": -500.0
                                         }
                                         """)// ¡Este es tu JSON malo!
                )
                .andExpect(status().isBadRequest());
    }
}
