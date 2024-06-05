package br.com.gabriel.desafio_celcoin.models.dtos;

import java.time.LocalDate;

import br.com.gabriel.desafio_celcoin.models.entities.Parcela;
import br.com.gabriel.desafio_celcoin.models.enums.ParcelaStatus;

public record ParcelaDTO(
    Double valorParcela,
    Short numParcela,
    LocalDate dataVencimento,
    LocalDate dataPagamento,
    ParcelaStatus status
) {
    public ParcelaDTO(Parcela parcela) {
        this(
            parcela.getValorParcela(),
            parcela.getNumParcela(),
            parcela.getDataVencimento(),
            parcela.getDataPagamento(),
            parcela.getStatus()
        );
    }
}
