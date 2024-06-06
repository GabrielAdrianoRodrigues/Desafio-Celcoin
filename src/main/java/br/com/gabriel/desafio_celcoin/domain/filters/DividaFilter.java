package br.com.gabriel.desafio_celcoin.domain.filters;

public record DividaFilter(
    Double valorTotal,
    Double valorMinimo,
    Double valorMaximo,
    String nomeCredor,
    Short diaVencimentoParcela,
    Short diaVencimentoParcelaMinima,
    Short diaVencimentoParcelaMaxima,
    Short numeroParcelas,
    Short numeroParcelasMinima,
    Short numeroParcelasMaxima,
    Short numeroParcelasPagas,
    Short numeroParcelasFaltantes,
    Short status
) {}
