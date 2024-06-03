package br.com.gabriel.desafio_celcoin.utils;

import br.com.gabriel.desafio_celcoin.models.entities.Divida;

public abstract class MathUtils {
    public static Double calcularValorParcela(Divida divida) {
        return divida.getValorTotal() / divida.getNumeroParcelas();
    }
}
