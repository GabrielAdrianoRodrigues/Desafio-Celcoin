package br.com.gabriel.desafio_celcoin.domain.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.gabriel.desafio_celcoin.domain.entities.Divida;
import br.com.gabriel.desafio_celcoin.domain.enums.DividaStatus;
import br.com.gabriel.desafio_celcoin.domain.enums.ParcelaStatus;
import br.com.gabriel.desafio_celcoin.utils.MathUtils;
import lombok.Getter;


@Getter
public class DividaDTO {
    private Long id;
    private Double valorTotal;
    private Double valorPresente;
    private String nomeCredor;
    private Short numeroParcelas;
    private DividaStatus status;
    private Integer parcelasPagas;
    private Integer parcelasVencidas;
    private List<ParcelaDTO> parcelas;

    public DividaDTO(Divida divida, List<ParcelaDTO> parcelas) {
        var totalParcelasPagas = parcelas.stream().filter(x -> x.status().equals(ParcelaStatus.PAGA)) .collect(Collectors.toList()).size();

        this.id = divida.getId();
        this.valorTotal = divida.getValorTotal();
        this.valorPresente = MathUtils.calcularValorPresente(divida, totalParcelasPagas);
        this.nomeCredor = divida.getNomeCredor();
        this.numeroParcelas = divida.getNumeroParcelas();
        this.status = divida.getStatus();
        this.parcelasPagas = totalParcelasPagas;
        this.parcelasVencidas = parcelas.stream().filter(x -> x.status().equals(ParcelaStatus.VENCIDA)).collect(Collectors.toList()).size();
        this.parcelas = parcelas;
    }
}
