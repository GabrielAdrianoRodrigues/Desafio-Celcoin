package br.com.gabriel.desafio_celcoin.controllers;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("parcela")
public class ParcelaController {
    
    @PutMapping
    public void pagarParcela() {

    }
}
