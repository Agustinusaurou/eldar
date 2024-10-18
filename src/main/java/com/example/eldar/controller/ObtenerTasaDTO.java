package com.example.eldar.controller;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ObtenerTasaDTO {
    String marcaTarjeta;
    Double importe;
}
