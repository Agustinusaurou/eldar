package com.example.eldar.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Operacion {
    private Long id;
    private Tarjeta tarjeta;
    private Double monto;

    public boolean esValida() {
        if (monto == null) {
            return false;
        }
        return monto < 1000;
    }

}
