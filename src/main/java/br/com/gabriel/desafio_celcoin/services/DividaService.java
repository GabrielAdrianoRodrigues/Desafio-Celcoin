package br.com.gabriel.desafio_celcoin.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabriel.desafio_celcoin.models.dtos.DividaDTO;
import br.com.gabriel.desafio_celcoin.models.dtos.ParcelaDTO;
import br.com.gabriel.desafio_celcoin.models.entities.Divida;
import br.com.gabriel.desafio_celcoin.models.entities.Parcela;
import br.com.gabriel.desafio_celcoin.models.forms.DividaForm;
import br.com.gabriel.desafio_celcoin.repositories.DividaRepository;
import br.com.gabriel.desafio_celcoin.repositories.ParcelaRepository;
import br.com.gabriel.desafio_celcoin.utils.MathUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class DividaService {
    @Autowired
    private DividaRepository dividaRepository;

    @Autowired
    private ParcelaRepository parcelaRepository;

    public DividaDTO registrarDivida(@Valid DividaForm form) {
        var divida = dividaRepository.save(new Divida(form));
        List<Parcela> parcelas = new ArrayList<>();
        IntStream.range(1, divida.getNumeroParcelas()+1).forEach(x -> {
            parcelas.add(
                Parcela.builder()
                    .dividaId(divida.getId())
                    .numParcela((short) x)
                    .valorParcela(MathUtils.calcularValorParcela(divida))
                .build()
            );
        });
        return new DividaDTO(divida, parcelaRepository.saveAll(parcelas).stream().map(ParcelaDTO::new).collect(Collectors.toList()));
    }

    public DividaDTO buscarDividaPorId(Long dividaId) {
        var divida = dividaRepository.findById(dividaId).orElseThrow(EntityNotFoundException::new);
        return new DividaDTO(divida, parcelaRepository.findByDividaId(divida.getId()).stream().map(ParcelaDTO::new).collect(Collectors.toList()));
    }
}
