package br.com.gabriel.desafio_celcoin.repositories;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import br.com.gabriel.desafio_celcoin.EmbeddedPostgres.EmbeddedPostgresConfiguration.EmbeddedPostgresExtension;
import br.com.gabriel.desafio_celcoin.EmbeddedPostgres.EmbeddedPostgresWithFlywayConfiguration;
import br.com.gabriel.desafio_celcoin.domain.entities.Divida;
import br.com.gabriel.desafio_celcoin.domain.entities.Parcela;
import br.com.gabriel.desafio_celcoin.domain.enums.DividaStatus;
import br.com.gabriel.desafio_celcoin.domain.enums.ParcelaStatus;
import br.com.gabriel.desafio_celcoin.repositories.divida.DividaRepository;

@DataJpaTest
@ExtendWith(EmbeddedPostgresExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { EmbeddedPostgresWithFlywayConfiguration.class })
public class DividaRepositoryTest {

    @Autowired
    private DividaRepository dividaRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Ao chamar a rotina de atualizacao deve atualizar o status para paga")
    void atualizarStatusDividaCaso1() {
        var divida = createDivida(
            Divida.builder()
                .valorTotal(10.0)
                .nomeCredor("teste")
                .numeroParcelas((short) 1)
                .taxaJuros(1.0)
            .build()
        );

        createParcela(
            Parcela.builder()
                .dividaId(divida.getId())
                .valorParcela(100.00)
                .numParcela((short) 1)
                .dataVencimento(LocalDate.of(1, Month.JANUARY, 1))
                .status(ParcelaStatus.PAGA)
            .build()
        );

        dividaRepository.atualizarStatusDivida(divida.getId());

        Assertions.assertEquals(DividaStatus.PAGA, buscarDividaPorId(divida.getId()).getStatus());
    }

    @Test
    @DisplayName("Ao chamar a rotina de atualizacao deve atualizar o status para vencida")
    void atualizarStatusCaso1() {
        var divida = createDivida(
            Divida.builder()
                .valorTotal(10.0)
                .nomeCredor("teste")
                .numeroParcelas((short) 1)
                .taxaJuros(1.0)
            .build()
        );

        var parcela = createParcela(
            Parcela.builder()
                .dividaId(divida.getId())
                .valorParcela(100.00)
                .numParcela((short) 1)
                .dataVencimento(LocalDate.of(1, Month.JANUARY, 1))
            .build()
        );

        dividaRepository.atualizarStatus();

        Assertions.assertEquals(DividaStatus.ATRASADA, buscarDividaPorId(divida.getId()).getStatus());
        Assertions.assertEquals(ParcelaStatus.VENCIDA, buscarParcelaPorId(parcela.getId()).getStatus());
    }

    private Parcela createParcela(Parcela parcela) {
        em.persist(parcela);
        return parcela;
    }

    private Divida createDivida(Divida divida) {
        em.persist(divida);
        return divida;
    }
    
    private Divida buscarDividaPorId(Long id) {
        return em.find(Divida.class, id);
    }

    private Parcela buscarParcelaPorId(Long id) {
        return em.find(Parcela.class, id);
    }
}
