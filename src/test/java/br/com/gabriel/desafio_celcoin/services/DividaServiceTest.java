package br.com.gabriel.desafio_celcoin.services;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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

import br.com.gabriel.desafio_celcoin.domain.dtos.DividaDTO;
import br.com.gabriel.desafio_celcoin.domain.dtos.Page;
import br.com.gabriel.desafio_celcoin.domain.entities.Divida;
import br.com.gabriel.desafio_celcoin.domain.enums.DividaStatus;
import br.com.gabriel.desafio_celcoin.domain.filters.DividaFilter;
import br.com.gabriel.desafio_celcoin.domain.filters.PageSpecification;
import br.com.gabriel.desafio_celcoin.domain.forms.DividaForm;
import br.com.gabriel.desafio_celcoin.repositories.ParcelaRepository;
import br.com.gabriel.desafio_celcoin.repositories.divida.DividaRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

@FlywayTest
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureEmbeddedDatabase(refresh = AFTER_EACH_TEST_METHOD)
public class DividaServiceTest {

    @Autowired
    @InjectMocks
    private DividaService dividaService;

    @Mock
    private DividaRepository dividaRepository;

    @Mock
    private ParcelaRepository parcelaRepository;
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao enviar todos os dados corretamente deve retornar uma DividaDTO")
    void registrarDividaCase1() {
        when(dividaRepository.save(any())).thenReturn(
            Divida.builder()
                .id(1l)
                .valorTotal(100.00)
                .nomeCredor("teste")
                .numeroParcelas((short) 10)
                .status(DividaStatus.ATIVA)
                .diaVencimentoParcela((short) 15)
                .taxaJuros(2.0)
            .build()
        );
        when(parcelaRepository.saveAll(any())).thenReturn(new ArrayList<>());
        Assertions.assertEquals(DividaDTO.class, dividaService.registrarDivida(new DividaForm(100.00, "teste", (short)10, (short) 15, 2.0)).getClass());
    }

    @Test
    @DisplayName("Ao enviar os dados corretamente de retornar uma pagina")
    void buscarDividasCase1() {
        when(dividaRepository.buscarDividas(any(), any())).thenReturn(Arrays.asList(
            Divida.builder()
                .id(1l)
                .valorTotal(100.00)
                .nomeCredor("teste")
                .numeroParcelas((short) 10)
                .status(DividaStatus.ATIVA)
                .diaVencimentoParcela((short) 15)
                .taxaJuros(2.0)
            .build()
        ));
        when(dividaRepository.buscarDividasCount(any())).thenReturn(1l);
        when(parcelaRepository.findAllByDividaId(any())).thenReturn(new ArrayList<>());
        Assertions.assertEquals(
            Page.class,
            dividaService.buscarDividas(
                new DividaFilter(null, null, null, null, null, null, null, null, null, null, null, null, null),
                new PageSpecification(10, 0)
            ).getClass()
        );
    }

    @Test
    @DisplayName("Ao informar um id valido deve retornar um DividaDTO")
    void buscarDividaPorIdCase1() {
        when(dividaRepository.findById(any())).thenReturn(
            Optional.of(
                Divida.builder()
                    .id(1l)
                    .valorTotal(100.00)
                    .nomeCredor("teste")
                    .numeroParcelas((short) 10)
                    .status(DividaStatus.ATIVA)
                    .diaVencimentoParcela((short) 15)
                    .taxaJuros(2.0)
                .build()
            )
        );
        when(parcelaRepository.findAllByDividaId(any())).thenReturn(new ArrayList<>());
        Assertions.assertEquals(DividaDTO.class, dividaService.buscarDividaPorId(1l).getClass());

    }
}
