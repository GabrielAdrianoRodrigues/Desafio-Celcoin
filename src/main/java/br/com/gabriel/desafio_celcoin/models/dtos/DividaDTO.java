package br.com.gabriel.desafio_celcoin.models.dtos;

import java.time.LocalDate;
import java.util.List;

import br.com.gabriel.desafio_celcoin.models.entities.Divida;


public record DividaDTO(
    Long id,
    Double valorTotal,
    String nomeCredor,
    LocalDate dataVencimento,
    Short numeroParcelas,
    List<ParcelaDTO> parcelas
) {
    public DividaDTO(Divida divida, List<ParcelaDTO> parcelas) {
        this(
            divida.getId(),
            divida.getValorTotal(),
            divida.getNomeCredor(),
            divida.getDataVencimento(),
            divida.getNumeroParcelas(),
            parcelas
        );
    }
}
