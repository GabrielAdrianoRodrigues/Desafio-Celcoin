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
            sql.append("AND (SELECT COUNT(*) FROM bu_parcelas bp WHERE bp.fk_divida_id = bd.id AND par_status = 1) = :parcelasPagas ");
        }

        if (filtro.numeroParcelasFaltantes() != null && filtro.numeroParcelasFaltantes() > 0) {
            sql.append("AND (SELECT COUNT(*) FROM bu_parcelas bp WHERE bp.fk_divida_id = bd.id AND par_status IN (0, 3)) >= :parcelasFaltantes ");
        }

        if (filtro.valorTotal() != null && filtro.valorTotal() > 0) {
            sql.append("AND bd.valor_total = :valorTotal ");
        } else if (filtro.valorMinimo() != null && filtro.valorMinimo() > 0 && filtro.valorMaximo() != null && filtro.valorMaximo() > 0) {
            sql.append("AND bd.valor_total BETWEEN :minValor AND :maxValor ");
        }

        if (filtro.nomeCredor() != null) {
            sql.append("AND bd.nm_credor ILIKE :nmCredor ");
        }

        if (filtro.diaVencimentoParcela() != null && filtro.diaVencimentoParcela() > 0) {
            sql.append("AND bd.dt_venc_parcela = :diaVencimentoParcela ");
        } else if (filtro.diaVencimentoParcelaMinima() != null && filtro.diaVencimentoParcelaMinima() > 0 && filtro.diaVencimentoParcelaMaxima() != null && filtro.diaVencimentoParcelaMaxima() > 0) {
            sql.append("AND bd.dt_venc_parcela BETWEEN :diaVencimentoParcelaMinima AND :diaVencimentoParcelaMaxima ");
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
            query.setParameter("nmParcelas", filtro.numeroParcelas());
        } else if (filtro.numeroParcelasMinima() != null && filtro.numeroParcelasMinima() > 0 && filtro.numeroParcelasMaxima() != null && filtro.numeroParcelasMaxima() > 0) {
            query.setParameter("minParcelas", filtro.numeroParcelasMinima());
            query.setParameter("maxParcelas", filtro.numeroParcelasMaxima());
        }
        
        if (filtro.numeroParcelasPagas() != null && filtro.numeroParcelasPagas() > 0) {
            query.setParameter("parcelasPagas", filtro.numeroParcelasPagas());
        }

        if (filtro.numeroParcelasFaltantes() != null && filtro.numeroParcelasFaltantes() > 0) {
            query.setParameter("parcelasFaltantes", filtro.numeroParcelasFaltantes());
        }

        if (filtro.valorTotal() != null && filtro.valorTotal() > 0) {
            query.setParameter("valorTotal", filtro.valorTotal());
        } else if (filtro.valorMinimo() != null && filtro.valorMinimo() > 0 && filtro.valorMaximo() != null && filtro.valorMaximo() > 0) {
            query.setParameter("minValor", filtro.valorMinimo());
            query.setParameter("maxValor", filtro.valorMaximo());
        }

        if (filtro.nomeCredor() != null) {
            query.setParameter(0, filtro.nomeCredor());
        }

        if (filtro.diaVencimentoParcela() != null && filtro.diaVencimentoParcela() > 0) {
            query.setParameter("diaVencimentoParcela", filtro.diaVencimentoParcela());
        } else if (filtro.diaVencimentoParcelaMinima() != null && filtro.diaVencimentoParcelaMinima() > 0 && filtro.diaVencimentoParcelaMaxima() != null && filtro.diaVencimentoParcelaMaxima() > 0) {
            query.setParameter("diaVencimentoParcelaMinima", filtro.diaVencimentoParcelaMinima());
            query.setParameter("diaVencimentoParcelaMaxima", filtro.diaVencimentoParcelaMaxima());
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
            sql.append("AND (SELECT COUNT(*) FROM bu_parcelas bp WHERE bp.fk_divida_id = bd.id AND par_status = 1) = :parcelasPagas ");
        }

        if (filtro.numeroParcelasFaltantes() != null && filtro.numeroParcelasFaltantes() > 0) {
            sql.append("AND (SELECT COUNT(*) FROM bu_parcelas bp WHERE bp.fk_divida_id = bd.id AND par_status IN (0, 3)) >= :parcelasFaltantes ");
        }

        if (filtro.valorTotal() != null && filtro.valorTotal() > 0) {
            sql.append("AND bd.valor_total = :valorTotal ");
        } else if (filtro.valorMinimo() != null && filtro.valorMinimo() > 0 && filtro.valorMaximo() != null && filtro.valorMaximo() > 0) {
            sql.append("AND bd.valor_total BETWEEN :minValor AND :maxValor ");
        }

        if (filtro.nomeCredor() != null) {
            sql.append("AND bd.nm_credor ILIKE :nmCredor ");
        }

        if (filtro.diaVencimentoParcela() != null && filtro.diaVencimentoParcela() > 0) {
            sql.append("AND bd.dt_venc_parcela = :diaVencimentoParcela ");
        } else if (filtro.diaVencimentoParcelaMinima() != null && filtro.diaVencimentoParcelaMinima() > 0 && filtro.diaVencimentoParcelaMaxima() != null && filtro.diaVencimentoParcelaMaxima() > 0) {
            sql.append("AND bd.dt_venc_parcela BETWEEN :diaVencimentoParcelaMinima AND :diaVencimentoParcelaMaxima ");
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
            query.setParameter("nmParcelas", filtro.numeroParcelas());
        } else if (filtro.numeroParcelasMinima() != null && filtro.numeroParcelasMinima() > 0 && filtro.numeroParcelasMaxima() != null && filtro.numeroParcelasMaxima() > 0) {
            query.setParameter("minParcelas", filtro.numeroParcelasMinima());
            query.setParameter("maxParcelas", filtro.numeroParcelasMaxima());
        }
        
        if (filtro.numeroParcelasPagas() != null && filtro.numeroParcelasPagas() > 0) {
            query.setParameter("parcelasPagas", filtro.numeroParcelasPagas());
        }

        if (filtro.numeroParcelasFaltantes() != null && filtro.numeroParcelasFaltantes() > 0) {
            query.setParameter("parcelasFaltantes", filtro.numeroParcelasFaltantes());
        }

        if (filtro.valorTotal() != null && filtro.valorTotal() > 0) {
            query.setParameter("valorTotal", filtro.valorTotal());
        } else if (filtro.valorMinimo() != null && filtro.valorMinimo() > 0 && filtro.valorMaximo() != null && filtro.valorMaximo() > 0) {
            query.setParameter("minValor", filtro.valorMinimo());
            query.setParameter("maxValor", filtro.valorMaximo());
        }

        if (filtro.nomeCredor() != null) {
            query.setParameter(0, filtro.nomeCredor());
        }

        if (filtro.diaVencimentoParcela() != null && filtro.diaVencimentoParcela() > 0) {
            query.setParameter("diaVencimentoParcela", filtro.diaVencimentoParcela());
        } else if (filtro.diaVencimentoParcelaMinima() != null && filtro.diaVencimentoParcelaMinima() > 0 && filtro.diaVencimentoParcelaMaxima() != null && filtro.diaVencimentoParcelaMaxima() > 0) {
            query.setParameter("diaVencimentoParcelaMinima", filtro.diaVencimentoParcelaMinima());
            query.setParameter("diaVencimentoParcelaMaxima", filtro.diaVencimentoParcelaMaxima());
        }

        return ((BigInteger)query.getSingleResult()).longValue();
    }
}