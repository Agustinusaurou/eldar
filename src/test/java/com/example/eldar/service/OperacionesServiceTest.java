package com.example.eldar.service;

import com.example.eldar.entities.Marca;
import com.example.eldar.entities.Tarjeta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatCode;
import java.time.LocalDate;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OperacionesServiceTest {
    @InjectMocks
    private OperacionesService sut;

    @DisplayName("agregarCalculadoraTasa")
    @Nested
    class AgregarCalculadoraTasaTest {
        @Test
        void when_nombreMarca_is_null_then_not_save_calculadoraTasa(){
            Function<LocalDate, Double> calculadoraTasa = fecha -> {
                Double tasa = (double) (fecha.getYear() % 100) / fecha.getMonthValue();
                if(tasa > 5) return 5D;
                if(tasa < 0.3) return 0.3;
                return tasa;
            };

            OperacionesService.agregarCalculadoraTasa(null, calculadoraTasa);

            assertThat(OperacionesService.getCalculadorasTasas().isEmpty()).isTrue();
        }

        @Test
        void when_nombreMarca_is_empty_then_not_save_calculadoraTasa(){
            Function<LocalDate, Double> calculadoraTasa = fecha -> {
                Double tasa = (double) (fecha.getYear() % 100) / fecha.getMonthValue();
                if(tasa > 5) return 5D;
                if(tasa < 0.3) return 0.3;
                return tasa;
            };

            OperacionesService.agregarCalculadoraTasa("", calculadoraTasa);

            assertThat(OperacionesService.getCalculadorasTasas().isEmpty()).isTrue();
        }

        @Test
        void when_calculadoraTasa_is_null_then_not_save_calculadoraTasa(){
            OperacionesService.agregarCalculadoraTasa("VISA", null);

            assertThat(OperacionesService.getCalculadorasTasas().isEmpty()).isTrue();
        }

        @Test
        void when_all_have_data_then_not_save_calculadoraTasa(){
            Function<LocalDate, Double> calculadoraTasa = fecha -> {
                Double tasa = (double) (fecha.getYear() % 100) / fecha.getMonthValue();
                if(tasa > 5) return 5D;
                if(tasa < 0.3) return 0.3;
                return tasa;
            };

            OperacionesService.agregarCalculadoraTasa("VISA", calculadoraTasa);

            assertThat(OperacionesService.getCalculadorasTasas()).hasSize(1);
        }
    }

    @DisplayName("obtenerInformacionTarjeta")
    @Nested
    class ObtenerInformacionTarjetaTest {
        @Test
        void when_tarjeta_has_data_then_return_string_whit_data(){
            Tarjeta tarjeta = Tarjeta.builder()
                .marca(Marca.builder().nombre("AMEX").build())
                .cardholder("JUAN PEREZ")
                .numero(1234)
                .fechaVencimiento(LocalDate.now())
                .build();

            String actual = sut.obtenerInformacionTarjeta(tarjeta);

            assertThat(actual).contains("La Tarjeta","con numero",", pertenece a","y tiene fecha de vencimiento el");
        }

        @Test
        void when_tarjeta_is_null_then_return_string_tarjeta_invalida(){
            String actual = sut.obtenerInformacionTarjeta(null);

            assertThat(actual).contains("Tarjeta invalida");
        }
    }

    @DisplayName("obtenerTasa")
    @Nested
    class ObtenerTasaTest {
        @Test
        void when_monto_is_greater_than_1000_then_throw_Exception(){
            Double monto = 1000.01;
            String marcaTarjeta = "VISA";

            assertThatCode(() -> sut.obtenerTasa(monto, marcaTarjeta)).message().contains("La operacion no es valida para el monto:");
        }

        @Test
        void when_monto_is_1000_then_throw_Exception(){
            Double monto = 1000D;
            String marcaTarjeta = "VISA";

            assertThatCode(() -> sut.obtenerTasa(monto, marcaTarjeta)).message().contains("La operacion no es valida para el monto:");
        }

        @Test
        void when_monto_is_null_then_throw_Exception(){
            Double monto = null;
            String marcaTarjeta = "VISA";

            assertThatCode(() -> sut.obtenerTasa(monto, marcaTarjeta)).message().contains("La operacion no es valida para el monto:");
        }

        @Test
        void when_marcaTarjeta_is_null_then_throw_Exception(){
            Double monto = 900D;
            String marcaTarjeta = null;

            assertThatCode(() -> sut.obtenerTasa(monto, marcaTarjeta)).message().contains("La marca no existe");
        }

        @Test
        void when_marcaTarjeta_is_empty_then_throw_Exception(){
            Double monto = 900D;
            String marcaTarjeta = "";

            assertThatCode(() -> sut.obtenerTasa(monto, marcaTarjeta)).message().contains("Marca no soportada:");
        }

        @Test
        void when_marcaTarjeta_is_not_exist_in_calculadorasTasas_then_throw_Exception(){
            Double monto = 900D;
            String marcaTarjeta = "VISA";

            assertThatCode(() -> sut.obtenerTasa(monto, marcaTarjeta)).message().contains("Marca no soportada:");
        }

        @Test
        void when_nothing_fail_then_return_tasa() throws Exception {
            Double monto = 900D;
            String marcaTarjeta = "VISA";

            Function<LocalDate, Double> calculadoraTasa = fecha -> 5D;

            OperacionesService.agregarCalculadoraTasa(marcaTarjeta, calculadoraTasa);

            double actual = sut.obtenerTasa(monto, marcaTarjeta);

            assertThat(actual).isEqualTo(45);
        }
    }
}
