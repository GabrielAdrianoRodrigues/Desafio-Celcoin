package br.com.gabriel.desafio_celcoin.repositories;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;

import java.time.LocalDate;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.com.gabriel.desafio_celcoin.domain.entities.Parcela;
import br.com.gabriel.desafio_celcoin.domain.enums.ParcelaStatus;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

@FlywayTest
@DataJpaTest
@AutoConfigureEmbeddedDatabase(refresh = AFTER_EACH_TEST_METHOD)
public class ParcelaRepositoryTest {

    @Autowired
    private ParcelaRepository parcelaRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Ao pagar uma parcela deve atualizar o status e a data de pagamento")
    void pagarParcelaCase1() {
        createParcela(
            Parcela.builder()
                .dividaId(1l)
                .valorParcela(100.00)
                .numParcela((short) 1)
                .dataVencimento(LocalDate.now())
            .build()
        );

        parcelaRepository.pagarParcela(1l);

        var parcela = buscarPorId(1l);
        Assertions.assertEquals(ParcelaStatus.PAGA, parcela.getStatus());
        Assertions.assertEquals(LocalDate.now(), parcela.getDataPagamento());
    }
    

    private Parcela createParcela(Parcela parcela) {
        em.persist(parcela);
        return parcela;
    }

    private Parcela buscarPorId(Long id) {
        return em.find(Parcela.class, id);
    }
}