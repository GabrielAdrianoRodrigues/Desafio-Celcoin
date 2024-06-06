package br.com.gabriel.desafio_celcoin.domain.filters;

import java.util.Optional;

public class PageSpecification {
    private Integer size;
    private Integer page;

    public Integer size() {
        return Optional.of(size).orElse(10);
    }

    public Integer page() {
        return Optional.of(page).orElse(0);
    }
}
