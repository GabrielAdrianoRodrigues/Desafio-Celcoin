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
    Short isPaga
) {
    public boolean containsValorFilter() {
        return (
            valorTotal  != null ||
            valorMinimo != null ||
            valorMaximo != null
        );
    }

    public boolean containsParcelasFilter() {
        return (
            numeroParcelas          != null || 
            numeroParcelasMinima    != null || 
            numeroParcelasMaxima    != null || 
            numeroParcelasPagas     != null ||
            numeroParcelasFaltantes != null
        );
    }

    public boolean containsDataVencimentoFilter() {
        return (
            dataVencimento       != null || 
            dataVencimentoMinima != null || 
            dataVencimentoMaxima != null 
        );
    }
}
