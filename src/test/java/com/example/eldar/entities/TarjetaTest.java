package com.example.eldar.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TarjetaTest {

    @DisplayName("esValida")
    @Nested
    class EsValidaTest {
        @Test
        void returns_true() {
            Tarjeta sut = Tarjeta
                .builder()
                .fechaVencimiento(LocalDate.of(3000,10,20))
                .build();

            boolean actual = sut.esValida();

            assertThat(actual).isTrue();
        }

        @Test
        void returns_false() {
            Tarjeta sut = Tarjeta
                .builder()
                .fechaVencimiento(LocalDate.of(2000,10,20))
                .build();

            boolean actual = sut.esValida();

            assertThat(actual).isFalse();
        }
    }

    @DisplayName("equals")
    @Nested
    class EqualsTest {
        @Test
        void when_param_is_not_Targeta_then_returns_false() {
            Tarjeta sut = Tarjeta
                .builder()
                .build();

            boolean actual = sut.equals(new Object());

            assertThat(actual).isFalse();
        }

        @Test
        void when_nombre_marca_is_not_equals_then_returns_false() {
            Tarjeta sut = Tarjeta
                .builder()
                .numero(1234)
                .cardholder("JUAN PEREZ")
                .marca(Marca.builder().nombre("VISA").build())
                .fechaVencimiento(LocalDate.of(3000,10,20))
                .build();

            Tarjeta tarjeta = Tarjeta
                .builder()
                .numero(1234)
                .cardholder("JUAN PEREZ")
                .marca(Marca.builder().nombre("AMEX").build())
                .fechaVencimiento(LocalDate.of(3000,10,20))
                .build();

            boolean actual = sut.equals(tarjeta);

            assertThat(actual).isFalse();
        }

        @Test
        void when_cardholder_is_not_equals_then_returns_false() {
            Tarjeta sut = Tarjeta
                .builder()
                .numero(1234)
                .cardholder("JUAN PEREZ1")
                .marca(Marca.builder().nombre("VISA").build())
                .fechaVencimiento(LocalDate.of(3000,10,20))
                .build();

            Tarjeta tarjeta = Tarjeta
                .builder()
                .numero(1234)
                .cardholder("JUAN PEREZ")
                .marca(Marca.builder().nombre("VISA").build())
                .fechaVencimiento(LocalDate.of(3000,10,20))
                .build();

            boolean actual = sut.equals(tarjeta);

            assertThat(actual).isFalse();
        }

        @Test
        void when_numero_is_not_equals_then_returns_false() {
            Tarjeta sut = Tarjeta
                .builder()
                .numero(4321)
                .cardholder("JUAN PEREZ")
                .marca(Marca.builder().nombre("VISA").build())
                .fechaVencimiento(LocalDate.of(3000,10,20))
                .build();

            Tarjeta tarjeta = Tarjeta
                .builder()
                .numero(1234)
                .cardholder("JUAN PEREZ")
                .marca(Marca.builder().nombre("VISA").build())
                .fechaVencimiento(LocalDate.of(3000,10,20))
                .build();

            boolean actual = sut.equals(tarjeta);

            assertThat(actual).isFalse();
        }

        @Test
        void when_fechaVencimiento_is_not_equals_then_returns_false() {
            Tarjeta sut = Tarjeta
                .builder()
                .numero(1234)
                .cardholder("JUAN PEREZ")
                .marca(Marca.builder().nombre("VISA").build())
                .fechaVencimiento(LocalDate.of(3000,10,19))
                .build();

            Tarjeta tarjeta = Tarjeta
                .builder()
                .numero(1234)
                .cardholder("JUAN PEREZ")
                .marca(Marca.builder().nombre("VISA").build())
                .fechaVencimiento(LocalDate.of(3000,10,20))
                .build();

            boolean actual = sut.equals(tarjeta);

            assertThat(actual).isFalse();
        }

        @Test
        void when_all_equals_then_returns_true() {
            Tarjeta sut = Tarjeta
                .builder()
                .numero(1234)
                .cardholder("JUAN PEREZ")
                .marca(Marca.builder().nombre("VISA").build())
                .fechaVencimiento(LocalDate.of(3000,10,20))
                .build();

            Tarjeta tarjeta = Tarjeta
                .builder()
                .numero(1234)
                .cardholder("JUAN PEREZ")
                .marca(Marca.builder().nombre("VISA").build())
                .fechaVencimiento(LocalDate.of(3000,10,20))
                .build();

            boolean actual = sut.equals(tarjeta);

            assertThat(actual).isTrue();
        }
    }
}
