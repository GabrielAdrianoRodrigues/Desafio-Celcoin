package br.com.gabriel.desafio_celcoin.repositories.divida;

import java.math.BigInteger;
import java.util.List;

import br.com.gabriel.desafio_celcoin.models.entities.Divida;
import br.com.gabriel.desafio_celcoin.models.filters.DividaFilter;
import br.com.gabriel.desafio_celcoin.models.filters.PageSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@SuppressWarnings("unchecked")
public class DividaRepositoryImpl implements DividaRepositoryCustom {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Divida> buscarDividas(DividaFilter filtro, PageSpecification pagina) {
        StringBuilder sql = new StringBuilder("SELECT * FROM bu_dividas bd WHERE 1=1");

        if (filtro.numeroParcelas() != null && filtro.numeroParcelas() > 0) {
            sql.append("AND bd.qtd_parcelas = :nmParcelas ");
        } else if (filtro.numeroParcelasMinima() != null && filtro.numeroParcelasMinima() > 0 && filtro.numeroParcelasMaxima() != null && filtro.numeroParcelasMaxima() > 0) {
            sql.append("AND bd.qtd_parcelas BETWEEN :minParcelas AND :maxParcelas ");
        }
        
        if (filtro.numeroParcelasPagas() != null && filtro.numeroParcelas() > 0) {
            sql.append("AND (SELECT COUNT(*) FROM bu_parcelas bp WHERE bp.fk_divida_id = bd.id AND is_paga IS TRUE) >= :parcelasPagas ");
        }

        if (filtro.numeroParcelasFaltantes() != null && filtro.numeroParcelasFaltantes() > 0) {
            sql.append("AND (SELECT COUNT(*) FROM bu_parcelas bp WHERE bp.fk_divida_id = bd.id AND is_paga IS FALSE) >= :parcelasFaltantes ");
        }

        if (filtro.valorTotal() != null && filtro.valorTotal() > 0) {
            sql.append("AND bd.valor_total = :valorTotal ");
        } else if (filtro.valorMinimo() != null && filtro.valorMinimo() > 0 && filtro.valorMaximo() != null && filtro.valorMaximo() > 0) {
            sql.append("AND bd.valor_total BETWEEN :minValor AND :maxValor ");
        }

        if (filtro.nomeCredor() != null) {
            sql.append("AND bd.nm_credor ILIKE :nmCredor ");
        }

        if (filtro.dataVencimento() != null) {
            sql.append("AND bd.dt_vencimento = :dtVencimento ");
        } else if (filtro.dataVencimentoMinima() != null && filtro.dataVencimentoMaxima() != null) {
            sql.append("AND bd.dt_vencimento BETWEEN :dtVencimentoMin AND :dtVencimentoMax ");
        }
        
        if(filtro.status() != null) {        
            sql.append(
                switch(filtro.status()) {
                    case 0 -> "AND bd.div_status = 0";
                    case 1 -> "AND bd.div_status = 1";
                    case 2 -> "AND bd.div_status = 2";
                    default -> "";
                }
            );
        }  

        sql.append("""
            LIMIT :size
            OFFSET (:size * :page)
        """);

        Query query = em.createNativeQuery(sql.toString(), Divida.class)
            .setParameter("size", pagina.size())
            .setParameter("page", pagina.page());

        if (filtro.numeroParcelas() != null && filtro.numeroParcelas() > 0) {
            query.setParameter(0, filtro.numeroParcelas());
        } else if (filtro.numeroParcelasMinima() != null && filtro.numeroParcelasMinima() > 0 && filtro.numeroParcelasMaxima() != null && filtro.numeroParcelasMaxima() > 0) {
            query.setParameter(0, filtro.numeroParcelasMinima());
            query.setParameter(0, filtro.numeroParcelasMaxima());
        }
        
        if (filtro.numeroParcelasPagas() != null && filtro.numeroParcelasPagas() > 0) {
            query.setParameter(0, filtro.numeroParcelasPagas());
        }

        if (filtro.numeroParcelasFaltantes() != null && filtro.numeroParcelasFaltantes() > 0) {
            query.setParameter(0, filtro.numeroParcelasFaltantes());
        }

        if (filtro.valorTotal() != null && filtro.valorTotal() > 0) {
            query.setParameter(0, filtro.valorTotal());
        } else if (filtro.valorMinimo() != null && filtro.valorMinimo() > 0 && filtro.valorMaximo() != null && filtro.valorMaximo() > 0) {
            query.setParameter(0, filtro.valorMinimo());
            query.setParameter(0, filtro.valorMaximo());
        }

        if (filtro.nomeCredor() != null) {
            query.setParameter(0, filtro.nomeCredor());
        }

        if (filtro.dataVencimento() != null) {
            query.setParameter(0, filtro.dataVencimento());
        } else if (filtro.dataVencimentoMinima() != null && filtro.dataVencimentoMaxima() != null) {
            query.setParameter(0, filtro.dataVencimentoMinima());
            query.setParameter(0, filtro.dataVencimentoMaxima());
        }

        return (List<Divida>) query.getResultList();
    }

    @Override
    public Long buscarDividasCount(DividaFilter filtro) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM bu_dividas bd WHERE bd.is_ativa IS TRUE");

        if (filtro.numeroParcelas() != null && filtro.numeroParcelas() > 0) {
            sql.append("AND bd.qtd_parcelas = :nmParcelas ");
        } else if (filtro.numeroParcelasMinima() != null && filtro.numeroParcelasMinima() > 0 && filtro.numeroParcelasMaxima() != null && filtro.numeroParcelasMaxima() > 0) {
            sql.append("AND bd.qtd_parcelas BETWEEN :minParcelas AND :maxParcelas ");
        }
        
        if (filtro.numeroParcelasPagas() != null && filtro.numeroParcelas() > 0) {
            sql.append("AND (SELECT COUNT(*) FROM bu_parcelas bp WHERE bp.fk_divida_id = bd.id AND is_paga IS TRUE) >= :parcelasPagas ");
        }

        if (filtro.numeroParcelasFaltantes() != null && filtro.numeroParcelasFaltantes() > 0) {
            sql.append("AND (SELECT COUNT(*) FROM bu_parcelas bp WHERE bp.fk_divida_id = bd.id AND is_paga IS FALSE) >= :parcelasFaltantes ");
        }

        if (filtro.valorTotal() != null && filtro.valorTotal() > 0) {
            sql.append("AND bd.valor_total = :valorTotal ");
        } else if (filtro.valorMinimo() != null && filtro.valorMinimo() > 0 && filtro.valorMaximo() != null && filtro.valorMaximo() > 0) {
            sql.append("AND bd.valor_total BETWEEN :minValor AND :maxValor ");
        }

        if (filtro.nomeCredor() != null) {
            sql.append("AND bd.nm_credor ILIKE :nmCredor ");
        }

        if (filtro.dataVencimento() != null) {
            sql.append("AND bd.dt_vencimento = :dtVencimento ");
        } else if (filtro.dataVencimentoMinima() != null && filtro.dataVencimentoMaxima() != null) {
            sql.append("AND bd.dt_vencimento BETWEEN :dtVencimentoMin AND :dtVencimentoMax ");
        }

        if(filtro.status() != null) {        
            sql.append(
                switch(filtro.status()) {
                    case 0 -> "AND bd.div_status = 0";
                    case 1 -> "AND bd.div_status = 1";
                    case 2 -> "AND bd.div_status = 2";
                    default -> "";
                }
            );
        } 

        Query query = em.createNativeQuery(sql.toString());

        if (filtro.numeroParcelas() != null && filtro.numeroParcelas() > 0) {
            query.setParameter(0, filtro.numeroParcelas());
        } else if (filtro.numeroParcelasMinima() != null && filtro.numeroParcelasMinima() > 0 && filtro.numeroParcelasMaxima() != null && filtro.numeroParcelasMaxima() > 0) {
            query.setParameter(0, filtro.numeroParcelasMinima());
            query.setParameter(0, filtro.numeroParcelasMaxima());
        }
        
        if (filtro.numeroParcelasPagas() != null && filtro.numeroParcelasPagas() > 0) {
            query.setParameter(0, filtro.numeroParcelasPagas());
        }

        if (filtro.numeroParcelasFaltantes() != null && filtro.numeroParcelasFaltantes() > 0) {
            query.setParameter(0, filtro.numeroParcelasFaltantes());
        }

        if (filtro.valorTotal() != null && filtro.valorTotal() > 0) {
            query.setParameter(0, filtro.valorTotal());
        } else if (filtro.valorMinimo() != null && filtro.valorMinimo() > 0 && filtro.valorMaximo() != null && filtro.valorMaximo() > 0) {
            query.setParameter(0, filtro.valorMinimo());
            query.setParameter(0, filtro.valorMaximo());
        }

        if (filtro.nomeCredor() != null) {
            query.setParameter(0, filtro.nomeCredor());
        }

        if (filtro.dataVencimento() != null) {
            query.setParameter(0, filtro.dataVencimento());
        } else if (filtro.dataVencimentoMinima() != null && filtro.dataVencimentoMaxima() != null) {
            query.setParameter(0, filtro.dataVencimentoMinima());
            query.setParameter(0, filtro.dataVencimentoMaxima());
        }

        return ((BigInteger)query.getSingleResult()).longValue();
    }
}