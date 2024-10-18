package com.example.eldar.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OperacionTest {
    @DisplayName("esValida")
    @Nested
    class EsValidaTest {
        @Test
        void when_monto_is_null_then_returns_false() {
            Operacion sut = Operacion
                .builder()
                .build();

            boolean actual = sut.esValida();

            assertThat(actual).isFalse();
        }

        @Test
        void when_monto_is_1000_then_returns_false() {
            Operacion sut = Operacion
                .builder()
                .monto(1000D)
                .build();

            boolean actual = sut.esValida();

            assertThat(actual).isFalse();
        }

        @Test
        void when_monto_is_greater_than_1000_then_returns_false() {
            Operacion sut = Operacion
                .builder()
                .monto(1001D)
                .build();

            boolean actual = sut.esValida();

            assertThat(actual).isFalse();
        }

        @Test
        void when_monto_is_less_than_1000_then_returns_true() {
            Operacion sut = Operacion
                .builder()
                .monto(900D)
                .build();

            boolean actual = sut.esValida();

            assertThat(actual).isTrue();
        }
    }
}
