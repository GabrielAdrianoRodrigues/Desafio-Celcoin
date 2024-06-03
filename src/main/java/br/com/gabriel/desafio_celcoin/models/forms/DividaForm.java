package br.com.gabriel.desafio_celcoin.models.forms;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DividaForm(
    @NotNull
    @Range(min = 0, max = Long.MAX_VALUE)
    Double valorTotal,
    @NotBlank
    String nomeCredor,
    @NotNull
    LocalDate dataVencimento,
    @NotNull
    @Range(min = 0, max = 60)
    Short numeroParcelas
) {}
