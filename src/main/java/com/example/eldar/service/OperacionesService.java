package com.example.eldar.service;

import com.example.eldar.entities.Operacion;
import com.example.eldar.entities.Tarjeta;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class OperacionesService {

    private static final Map<String, Function<LocalDate, Double>> calculadorasTasas = new HashMap<>();

    public static void agregarCalculadoraTasa(String nombreMarca, Function<LocalDate, Double> calculadoraTasa) {
        if (nombreMarca == null || nombreMarca.isEmpty() || calculadoraTasa == null) {
            return;
        }
        calculadorasTasas.put(nombreMarca.toUpperCase(), calculadoraTasa);
    }

    public static Map<String, Function<LocalDate, Double>> getCalculadorasTasas() {
        return calculadorasTasas;
    }

    public String obtenerInformacionTarjeta(Tarjeta tarjeta) {
        if (tarjeta == null) {
            return "Tarjeta invalida";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        String formattedString = tarjeta.getFechaVencimiento().format(formatter);

        return String.format("La Tarjeta %s con numero %d, pertenece a %s y tiene fecha de vencimiento el %s",
            tarjeta.getMarca().getNombre(), tarjeta.getNumero(), tarjeta.getCardholder(), formattedString);
    }

    public double obtenerTasa(Double monto, String marcaTarjeta) throws Exception {
        Operacion operacion = Operacion.builder()
            .monto(monto)
            .build();

        if (!operacion.esValida()) {
            throw new Exception("La operacion no es valida para el monto: " + monto);
        }

        if (marcaTarjeta == null) {
            throw new Exception("La marca no existe");
        }

        Function<LocalDate, Double> calculadoraTasa = calculadorasTasas.get(marcaTarjeta.toUpperCase());

        if (calculadoraTasa == null) {
            throw new Exception("Marca no soportada: " + marcaTarjeta);
        }

        Double tasa = calculadoraTasa.apply(LocalDate.now());
        return monto * (tasa / 100);
    }
}
