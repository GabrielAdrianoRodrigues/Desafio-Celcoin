package br.com.gabriel.desafio_celcoin.utils;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateUtilsTest {
    
    @Test
    @DisplayName("Ao informar uma data invalida em um feriado deve retornar o proximo dia util")
    void calcularDataProximaParcelaCaso1() {
        Assertions.assertEquals(LocalDate.of(2024, Month.DECEMBER, 26), DateUtils.calcularDataProximaParcela(LocalDate.of(2024, Month.NOVEMBER, 25), (short) 25));
    }

    @Test
    @DisplayName("Ao informar uma data invalida em um feriado ao sabado deve retornar o proximo dia util")
    void calcularDataProximaParcelaCase2() {
        Assertions.assertEquals(LocalDate.of(2024, Month.SEPTEMBER, 9), DateUtils.calcularDataProximaParcela(LocalDate.of(2024, Month.AUGUST, 7), (short) 7));
    }

    @Test
    @DisplayName("Ao informar 29 de janeiro em um ano bisexto deve retornar dia 29 de fevereiro")
    void calcularDataProximaParcelaCase3() {
        Assertions.assertEquals(LocalDate.of(2024, Month.FEBRUARY, 29), DateUtils.calcularDataProximaParcela(LocalDate.of(2024, Month.JANUARY, 29), (short) 29));
    }

    @Test
    @DisplayName("Ao informar 29 de janeiro em um ano comum deve retornar dia 1 de marco")
    void calcularDataProximaParcelaCase4() {
        Assertions.assertEquals(LocalDate.of(2023, Month.MARCH, 1), DateUtils.calcularDataProximaParcela(LocalDate.of(2023, Month.JANUARY, 29), (short) 29));
    }
}

