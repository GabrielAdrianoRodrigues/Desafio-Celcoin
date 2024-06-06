package br.com.gabriel.desafio_celcoin.domain.forms;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PagamentoForm(
    @NotNull
    Long dividaId, 
    @Min(0)
    @NotNull
    Short numParcela,
    @NotNull
    Double valor
) 
{}