package br.com.gabriel.desafio_celcoin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabriel.desafio_celcoin.models.forms.PagamentoForm;
import br.com.gabriel.desafio_celcoin.services.ParcelaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("parcela")
@SecurityRequirement(name = "bearer-key")
public class ParcelaController {

    @Autowired
    private ParcelaService parcelaService;
    
    @PatchMapping("pagar/{dividaId}/{numParcela}")
    public ResponseEntity<String> pagarParcela(@PathVariable("dividaId") Long dividaId, @PathVariable("numParcela") Short numParcela) {
        return ResponseEntity.ok(parcelaService.pagarParcela(dividaId, numParcela));
    }

    @PutMapping("pagar/valor")
    public ResponseEntity<String> pagarParcelaComValor(@RequestBody@Valid PagamentoForm form) {
        return ResponseEntity.ok(parcelaService.pagarParcelaComValor(form));
    }
}
