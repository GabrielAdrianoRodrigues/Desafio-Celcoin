package br.com.gabriel.desafio_celcoin.models.dtos;

import br.com.gabriel.desafio_celcoin.models.entities.Parcela;

public record ParcelaDTO(
    Double valorParcela,
    Short numParcela,
    Boolean isPaga
) {
    public ParcelaDTO(Parcela parcela) {
        this(
            parcela.getValorParcela(),
            parcela.getNumParcela(),
            parcela.getIsPaga()
        );
    }
}
