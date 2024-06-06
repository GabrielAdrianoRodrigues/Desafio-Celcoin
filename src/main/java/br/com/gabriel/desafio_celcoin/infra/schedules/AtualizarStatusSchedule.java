package br.com.gabriel.desafio_celcoin.infra.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.gabriel.desafio_celcoin.repositories.divida.DividaRepository;

@Component
public class AtualizarStatusSchedule {
 
    @Autowired
    private DividaRepository dividaRepository;

    @Scheduled(cron = "0 0 1 * * *")
    public void atualizarStatus() {
        dividaRepository.atualizarStatus();
    }
}
