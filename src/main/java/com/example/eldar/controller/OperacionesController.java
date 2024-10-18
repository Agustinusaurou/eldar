package com.example.eldar.controller;

import com.example.eldar.service.OperacionesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class OperacionesController {

    private final OperacionesService operacionesService;

    @Autowired
    OperacionesController(OperacionesService operacionesService) {
        this.operacionesService = operacionesService;
    }

    @PostMapping("/obtenerTasa")
    public ResponseEntity<Double> obtenerTasa(@RequestBody ObtenerTasaDTO dto) {
        try {
            Double tasa = operacionesService.obtenerTasa(dto.getImporte(), dto.getMarcaTarjeta());
            return ResponseEntity.ok(tasa);
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }
}
