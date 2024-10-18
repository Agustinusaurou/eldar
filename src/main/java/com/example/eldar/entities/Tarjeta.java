package com.example.eldar.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Tarjeta {
    private Long id;
    private Integer numero;
    private String cardholder;
    private LocalDate fechaVencimiento;
    private Marca marca;

    public boolean esValida() {
        return fechaVencimiento.isAfter(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tarjeta)) {
            return false;
        }

        Tarjeta t = (Tarjeta) o;

        return this.marca.getNombre().equals(t.getMarca().getNombre()) &&
            this.numero.equals(t.getNumero()) &&
            this.cardholder.equals(t.getCardholder()) &&
            this.fechaVencimiento.equals(t.getFechaVencimiento());
    }
}
