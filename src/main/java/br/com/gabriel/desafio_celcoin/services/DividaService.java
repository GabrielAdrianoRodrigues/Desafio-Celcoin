package br.com.gabriel.desafio_celcoin.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabriel.desafio_celcoin.models.dtos.DividaDTO;
import br.com.gabriel.desafio_celcoin.models.dtos.Page;
import br.com.gabriel.desafio_celcoin.models.dtos.ParcelaDTO;
import br.com.gabriel.desafio_celcoin.models.entities.Divida;
import br.com.gabriel.desafio_celcoin.models.entities.Parcela;
import br.com.gabriel.desafio_celcoin.models.filters.DividaFilter;
import br.com.gabriel.desafio_celcoin.models.filters.PageSpecification;
import br.com.gabriel.desafio_celcoin.models.forms.DividaForm;
import br.com.gabriel.desafio_celcoin.repositories.ParcelaRepository;
import br.com.gabriel.desafio_celcoin.repositories.divida.DividaRepository;
import br.com.gabriel.desafio_celcoin.utils.DateUtils;
import br.com.gabriel.desafio_celcoin.utils.MathUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class DividaService {
    @Autowired
    private DividaRepository dividaRepository;

    @Autowired
    private ParcelaRepository parcelaRepository;

    public Page<DividaDTO> buscarDividas(DividaFilter filtro, PageSpecification pagina) {
        return new Page<DividaDTO>(
            dividaRepository.buscarDividas(filtro, pagina).stream().map(x -> {
                return new DividaDTO(x, parcelaRepository.findAllByDividaId(x.getId()).stream().map(ParcelaDTO::new).collect(Collectors.toList()));
            }).collect(Collectors.toList()),
            dividaRepository.buscarDividasCount(filtro),
            pagina.size(),
            pagina.page()
        );
    }

    public DividaDTO buscarDividaPorId(Long dividaId) {
        return new DividaDTO(
            dividaRepository.findById(dividaId).orElseThrow(EntityNotFoundException::new),
            parcelaRepository.findAllByDividaId(dividaId).stream().map(ParcelaDTO::new).collect(Collectors.toList())
        );
    }

    public DividaDTO registrarDivida(@Valid DividaForm form) {
        var divida = dividaRepository.save(new Divida(form));
        List<Parcela> parcelas = new ArrayList<>();
        IntStream.range(0, divida.getNumeroParcelas()).forEach(x -> {
            parcelas.add(
                Parcela.builder()
                    .dividaId(divida.getId())
                    .numParcela((short) x)
                    .valorParcela(MathUtils.calcularValorParcela(divida))
                    .dataVencimento(DateUtils.calcularDataProximaParcela(
                            x == 0 ? LocalDate.now() : parcelas.get(x-1).getDataVencimento(), 
                            divida.getDiaVencimentoParcela()
                        )
                    )
                .build()
            );
        });
        return new DividaDTO(divida, parcelaRepository.saveAll(parcelas).stream().map(ParcelaDTO::new).collect(Collectors.toList()));
    }
}
