package br.com.gabriel.desafio_celcoin.domain.forms;

import jakarta.validation.constraints.NotBlank;

public record UsuarioForm(@NotBlank String email, @NotBlank String password) {}
