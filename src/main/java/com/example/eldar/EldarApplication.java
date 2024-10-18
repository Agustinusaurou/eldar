package com.example.eldar;

import com.example.eldar.entities.Marca;
import com.example.eldar.entities.Operacion;
import com.example.eldar.entities.Tarjeta;
import com.example.eldar.service.OperacionesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@Slf4j
@SpringBootApplication
public class EldarApplication {

    private static OperacionesService operacionesService;

    @Autowired
    public EldarApplication(OperacionesService operacionesService) {
        EldarApplication.operacionesService = operacionesService;
    }

    public static void main(String[] args) {

        OperacionesService.agregarCalculadoraTasa("VISA", fecha -> {
            Double tasa = (double) (fecha.getYear() % 100) / fecha.getMonthValue();
            if(tasa > 5) return 5D;
            if(tasa < 0.3) return 0.3;
            return tasa;
        });

        OperacionesService.agregarCalculadoraTasa("AMEX", fecha -> {
            Double tasa =  fecha.getDayOfMonth() * 0.5;
            if(tasa > 5) return 5D;
            if(tasa < 0.3) return 0.3;
            return tasa;

        });

        OperacionesService.agregarCalculadoraTasa("NARA", fecha -> {
            Double tasa =  fecha.getMonthValue() * 0.1;
            if(tasa > 5) return 5D;
            if(tasa < 0.3) return 0.3;
            return tasa;
        });

        Tarjeta tarjetaVisa = Tarjeta.builder()
            .id(1L)
            .numero(123456789)
            .cardholder("JUAN PEREZ")
            .fechaVencimiento(LocalDate.of(2025, 10, 1))
            .marca(Marca.builder()
                .id(1L)
                .nombre("VISA")
                .build())
            .build();

        Tarjeta tarjetaAmex = Tarjeta.builder()
            .id(2L)
            .numero(987654321)
            .cardholder("JULIETA GIMENEZ")
            .fechaVencimiento(LocalDate.of(2027, 2, 1))
            .marca(Marca.builder()
                .id(2L)
                .nombre("AMEX")
                .build())
            .build();

        Operacion operacion = Operacion.builder()
            .id(1L)
            .monto(1500D)
            .tarjeta(tarjetaAmex)
            .build();

        SpringApplication.run(EldarApplication.class, args);

        String informacionTarjeta = operacionesService.obtenerInformacionTarjeta(tarjetaVisa);
        log.info(informacionTarjeta);

        if (operacion.esValida()) {
            log.info(String.format("la operacion con monto %f es valida",
                operacion.getMonto()));
        } else {
            log.info(String.format("la operacion con monto %f no es valida",
                operacion.getMonto()));
        }

        if (tarjetaAmex.esValida()) {
            log.info(String.format("la tarjeta %s es valida para operar",
                operacion.getTarjeta().getMarca().getNombre()));
        } else {
            log.info(String.format("la tarjeta %s no es valida para operar",
                operacion.getTarjeta().getMarca().getNombre()));
        }

        if (tarjetaVisa.equals(tarjetaAmex)) {
            log.info("Las tarjetas son iguales");
        } else {
            log.info("Las tarjetas son distintas");
        }

        try {
            double tasa = operacionesService.obtenerTasa(900D, "VISA");
            log.info("Tasa aplicada: " + tasa);
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }

    }

}
