package br.com.gabriel.desafio_celcoin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabriel.desafio_celcoin.models.forms.PagamentoForm;
import br.com.gabriel.desafio_celcoin.repositories.ParcelaRepository;
import br.com.gabriel.desafio_celcoin.repositories.divida.DividaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class ParcelaService {
    @Autowired
    private ParcelaRepository parcelaRepository;

    @Autowired
    private DividaRepository dividaRepository;

    public String pagarParcela(Long dividaId, Short numParcela) {
        parcelaRepository.pagarParcela(
            parcelaRepository.findByDividaIdAndNumParcela(
                dividaId, 
                numParcela
            ).orElseThrow(EntityNotFoundException::new).getId()
        );
        dividaRepository.atualizarStatusDivida(dividaId);
        return "Pagamento realizado com sucesso";
    } 

    public String pagarParcelaComValor(@Valid PagamentoForm form) {
        var parcela = parcelaRepository.findByDividaIdAndNumParcela(form.dividaId(), form.numParcela()).orElseThrow(EntityNotFoundException::new);
        if(parcela.getValorParcela() > form.valor()) {
            return "Saldo insuficente";
        }
        parcelaRepository.pagarParcela(parcela.getId());
        dividaRepository.atualizarStatusDivida(form.dividaId());
        return "Pagamento realizado com sucesso";
    }
}
