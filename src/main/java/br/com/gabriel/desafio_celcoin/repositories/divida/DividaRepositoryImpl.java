package br.com.gabriel.desafio_celcoin.repositories.divida;

import java.util.List;

import br.com.gabriel.desafio_celcoin.config.PageSpecification;
import br.com.gabriel.desafio_celcoin.models.entities.Divida;
import br.com.gabriel.desafio_celcoin.models.filters.DividaFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class DividaRepositoryImpl implements DividaRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Divida> buscarDividas(DividaFilter filtro, PageSpecification pagina) {
        StringBuilder sql = new StringBuilder("SELECT * FROM bu_dividas bu WHERE bu.is_ativa IS TRUE");

        if(filtro.containsValorFilter()) {
        }

        if(filtro.containsParcelasFilter()) {
        }

        if(filtro.containsDataVencimentoFilter()) {
        }
        
        return null;
    }
}
