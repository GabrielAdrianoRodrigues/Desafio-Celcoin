package br.com.gabriel.desafio_celcoin.repositories.divida;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabriel.desafio_celcoin.models.entities.Divida;

public interface DividaRepository extends JpaRepository<Divida, Long>, DividaRepositoryCustom {

}
