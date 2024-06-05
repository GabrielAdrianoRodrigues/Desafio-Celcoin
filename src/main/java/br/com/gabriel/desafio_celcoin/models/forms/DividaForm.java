package br.com.gabriel.desafio_celcoin.models.forms;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DividaForm(
    @NotNull
    @Range
    Double valorTotal,
    @NotBlank
    String nomeCredor,
    @NotNull
    @Range(max = 60)
    Short numeroParcelas,
    @Range(min = 1, max = 31)
    Short diaVencimentoParcela,
    @Range(min = 0, max = 2)
    Double taxaJuros
) {}
