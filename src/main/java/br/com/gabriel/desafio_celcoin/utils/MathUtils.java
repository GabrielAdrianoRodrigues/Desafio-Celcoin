package br.com.gabriel.desafio_celcoin.utils;

import br.com.gabriel.desafio_celcoin.domain.entities.Divida;

public abstract class MathUtils {
    public static Double calcularValorParcela(Divida divida) {
        return divida.getValorTotal() / divida.getNumeroParcelas();
    }

    public static Double calcularValorPresente(Divida divida, Integer parcelasPagas) {
       return  Math.floor(divida.getValorTotal() / Math.pow((1.0 + (divida.getTaxaJuros() / 100)), parcelasPagas) * 100) / 100;
    }
}
