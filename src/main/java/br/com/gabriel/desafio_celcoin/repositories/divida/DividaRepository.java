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
}
