package br.com.gabriel.desafio_celcoin.services;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.gabriel.desafio_celcoin.domain.entities.Parcela;
import br.com.gabriel.desafio_celcoin.domain.forms.PagamentoForm;
import br.com.gabriel.desafio_celcoin.repositories.ParcelaRepository;
import br.com.gabriel.desafio_celcoin.repositories.divida.DividaRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

@FlywayTest
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureEmbeddedDatabase(refresh = AFTER_EACH_TEST_METHOD)
public class ParcelaServiceTest {
    @Autowired
    @InjectMocks
    private ParcelaService parcelaService;

    @Mock
    private ParcelaRepository parcelaRepository;

    @Mock
    private DividaRepository dividaRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao tentar realizar um pagamento com um valor abaixo do valor da parcela deve retornar uma mensagem de saldo insuficiente")
    void pagarParcelaComValorCase1() {
        when(parcelaRepository.findByDividaIdAndNumParcela(any(), any())).thenReturn(Optional.of(Parcela.builder().valorParcela(100.00).build()));
        Assertions.assertEquals("Saldo insuficente", parcelaService.pagarParcelaComValor(new PagamentoForm(any(), any(), 10.0)));
    }

    @Test
    @DisplayName("Ao tentar realizar um pagamento com um valor abaixo do valor da parcela deve retornar uma mensagem de pagamento realizado com sucesso")
    void pagarParcelaComValorCase2() {
        when(parcelaRepository.findByDividaIdAndNumParcela(any(), any())).thenReturn(Optional.of(Parcela.builder().valorParcela(1.00).build()));
        Assertions.assertEquals("Pagamento realizado com sucesso", parcelaService.pagarParcelaComValor(new PagamentoForm(any(), any(), 10.0)));
    }
}
