package br.com.gabriel.desafio_celcoin.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gabriel.desafio_celcoin.domain.entities.Parcela;
import jakarta.transaction.Transactional;


public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
    List<Parcela> findAllByDividaId(Long dividaId);
    Optional<Parcela> findByDividaIdAndNumParcela(Long dividaId, Short numParcela);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE bu_parcelas SET
                par_status = 1,
                dt_pagamento = now()
            WHERE id = :parcelaId
        """
        , nativeQuery = true
    )
    void pagarParcela(@Param("parcelaId") Long parcelaId);
}
