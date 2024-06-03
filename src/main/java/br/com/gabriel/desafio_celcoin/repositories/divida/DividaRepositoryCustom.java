package br.com.gabriel.desafio_celcoin.repositories.divida;

import java.util.List;

import br.com.gabriel.desafio_celcoin.config.PageSpecification;
import br.com.gabriel.desafio_celcoin.models.entities.Divida;
import br.com.gabriel.desafio_celcoin.models.filters.DividaFilter;

public interface DividaRepositoryCustom {

    List<Divida> buscarDividas(DividaFilter filtro, PageSpecification pagina);
}
