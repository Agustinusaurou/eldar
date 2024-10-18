package com.example.eldar.controller;

import com.example.eldar.service.OperacionesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OperacionesControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Mock
    private OperacionesService operacionesService;
    private MockMvc mockMvc;

    private static String asJsonString(Object request) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(request);
    }

    @BeforeEach
    void setUpForEachTest() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(new OperacionesController(operacionesService))
            .build();
    }

    @DisplayName("obtenerTasa")
    @Nested
    class ObtenerTasaTest {
        @Test
        void when_operacionesService_throws_exception_then_return_status_PRECONDITION_FAILED() throws Exception {
            ObtenerTasaDTO dto = ObtenerTasaDTO.builder()
                .importe(100D)
                .marcaTarjeta("VISA")
                .build();

            when(operacionesService.obtenerTasa(anyDouble(), anyString())).thenThrow(new Exception("exception"));

            mockMvc.perform(post("/obtenerTasa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isPreconditionFailed());

            verify(operacionesService).obtenerTasa(100D, "VISA");
        }

        @Test
        void when_operacionesService_return_double_then_return_status_OK() throws Exception {
            ObtenerTasaDTO dto = ObtenerTasaDTO.builder()
                .importe(100D)
                .marcaTarjeta("VISA")
                .build();

            when(operacionesService.obtenerTasa(anyDouble(), anyString())).thenReturn(5D);

            mockMvc.perform(post("/obtenerTasa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$"), is(5D)));

            verify(operacionesService).obtenerTasa(100D, "VISA");
        }
    }
}
