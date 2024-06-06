package br.com.gabriel.desafio_celcoin.domain.dtos;

import java.util.List;

public record Page<T> (
    List<T> content,
    Long totalElements,
    Integer size,
    Integer page
){}
