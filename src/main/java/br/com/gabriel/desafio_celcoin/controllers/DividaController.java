package br.com.gabriel.desafio_celcoin.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.gabriel.desafio_celcoin.models.filters.DividaFilter;
import br.com.gabriel.desafio_celcoin.models.filters.PageSpecification;
import br.com.gabriel.desafio_celcoin.models.forms.DividaForm;
import br.com.gabriel.desafio_celcoin.services.DividaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("divida")    
public class DividaController {
    
    @Autowired
    private DividaService dividaService; 
    
    @GetMapping
    public ResponseEntity<?> buscarDividas(DividaFilter filtro, PageSpecification pagina) {
        return ResponseEntity.ok(dividaService.buscarDividas(filtro, pagina));
    }

    @GetMapping("{dividaId}")
    public ResponseEntity<?> buscarDividaPorId(@PathVariable Long dividaId) {
        return ResponseEntity.ok(dividaService.buscarDividaPorId(dividaId));
    }
    
    @PostMapping
    public ResponseEntity<?> registrarDivida(@RequestBody@Valid DividaForm form, UriComponentsBuilder uriBuilder) {
        var dto = dividaService.registrarDivida(form);
        return ResponseEntity.created(uriBuilder.path("divida/{dividaId}").buildAndExpand(dto.getId()).toUri()).body(dto);
    }
}
