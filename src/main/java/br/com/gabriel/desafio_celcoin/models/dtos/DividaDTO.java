package br.com.gabriel.desafio_celcoin.models.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.gabriel.desafio_celcoin.models.entities.Divida;
import br.com.gabriel.desafio_celcoin.models.enums.DividaStatus;
import br.com.gabriel.desafio_celcoin.models.enums.ParcelaStatus;
import br.com.gabriel.desafio_celcoin.utils.MathUtils;
import lombok.Getter;


@Getter
public class DividaDTO {
    private Long id;
    private Double valorTotal;
    private Double valorPresente;
    private Double valorFaltante;
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
        this.valorFaltante = parcelas.stream().filter(x -> x.status().equals(ParcelaStatus.PAGA)).reduce(0.0, (subTotal, parcela) -> subTotal + parcela.valorParcela(), Double::sum);
        this.nomeCredor = divida.getNomeCredor();
        this.numeroParcelas = divida.getNumeroParcelas();
        this.status = divida.getStatus();
        this.parcelasPagas = totalParcelasPagas;
        this.parcelasVencidas = parcelas.stream().filter(x -> x.status().equals(ParcelaStatus.VENCIDA)).collect(Collectors.toList()).size();
        this.parcelas = parcelas;
    }
}
