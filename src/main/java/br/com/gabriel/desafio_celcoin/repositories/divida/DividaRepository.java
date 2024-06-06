package br.com.gabriel.desafio_celcoin.repositories.divida;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gabriel.desafio_celcoin.models.entities.Divida;

public interface DividaRepository extends JpaRepository<Divida, Long>, DividaRepositoryCustom {
    @Modifying
    @Query(value = """
            UPDATE bu_dividas SET
                div_status = 1
            WHERE id = :dividaId
                AND qtd_parcelas = (
                    SELECT COUNT(*) FROM bu_parcelas
                    WHERE par_status = 1
                        AND fk_divida_id = :dividaId
                )
        """, nativeQuery = true
    )
    void atualizarStatusDivida(@Param("dividaId") Long dividaId);

    @Modifying
    @Query(value = """
            DO LANGUAGE plpgsql $$
                BEGIN
                    UPDATE bu_parcelas SET
                        par_status = 2
                    WHERE dt_vencimento < now()
                        AND par_status = 0;

                    UPDATE bu_dividas bd SET
                        div_status = 2
                    WHERE bd.div_status = 0
                        AND EXISTS(
                            SELECT FROM bu_parcelas bp
                            WHERE bp.par_status = 2
                                AND bp.fk_divida_id = bd.id
                        );
                END
            $$;
        """
        , nativeQuery = true    
    )
    void atualizarStatus();

    // @Procedure("atualizar_status")
    // void atualizarStatus();
}
