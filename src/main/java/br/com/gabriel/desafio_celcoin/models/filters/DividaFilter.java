package br.com.gabriel.desafio_celcoin.models.filters;

import java.time.LocalDate;

public record DividaFilter(
    Double valorTotal,
    Double valorMinimo,
    Double valorMaximo,
    String nomeCredor,
    LocalDate dataVencimento,
    LocalDate dataVencimentoMinima,
    LocalDate dataVencimentoMaxima,
    Short numeroParcelas,
    Short numeroParcelasMinima,
    Short numeroParcelasMaxima,
    Short numeroParcelasPagas,
    Short numeroParcelasFaltantes,
    Short status
) {}
