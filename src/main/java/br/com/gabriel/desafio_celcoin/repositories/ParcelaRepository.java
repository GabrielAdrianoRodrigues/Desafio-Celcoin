package br.com.gabriel.desafio_celcoin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabriel.desafio_celcoin.models.entities.Parcela;
import java.util.List;


public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
    List<Parcela> findAllByDividaId(Long dividaId);
}
