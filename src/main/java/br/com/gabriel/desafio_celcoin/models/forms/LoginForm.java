package br.com.gabriel.desafio_celcoin.models.forms;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;

public record LoginForm(@NotBlank @JsonAlias("email") String email, @NotBlank @JsonAlias("senha") String password) {}
