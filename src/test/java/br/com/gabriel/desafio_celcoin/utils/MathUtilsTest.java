package br.com.gabriel.desafio_celcoin.utils;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import br.com.gabriel.desafio_celcoin.domain.entities.Divida;

public class MathUtilsTest {
    
    @Test
    @DisplayName("Ao informar uma divida e quantas parcelas faltam, informará o valor presente")
    void calcularValorPresenteCaso1() {
        var vp = MathUtils.calcularValorPresente(
            Divida.builder()
                .taxaJuros(2.00)
                .valorTotal(10000.00)
            .build()
            , 11
        );
        Assertions.assertEquals(8042.63, vp);
    }
}
