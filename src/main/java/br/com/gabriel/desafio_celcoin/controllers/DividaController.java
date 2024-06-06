package br.com.gabriel.desafio_celcoin.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.gabriel.desafio_celcoin.domain.dtos.DividaDTO;
import br.com.gabriel.desafio_celcoin.domain.dtos.Page;
import br.com.gabriel.desafio_celcoin.domain.filters.DividaFilter;
import br.com.gabriel.desafio_celcoin.domain.filters.PageSpecification;
import br.com.gabriel.desafio_celcoin.domain.forms.DividaForm;
import br.com.gabriel.desafio_celcoin.services.DividaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("divida")    
@SecurityRequirement(name = "bearer-key")
public class DividaController {
    
    @Autowired
    private DividaService dividaService; 
    
    @GetMapping
    public ResponseEntity<Page<DividaDTO>> buscarDividas(DividaFilter filtro, PageSpecification pagina) {
        return ResponseEntity.ok(dividaService.buscarDividas(filtro, pagina));
    }

    @GetMapping("{dividaId}")
    public ResponseEntity<DividaDTO> buscarDividaPorId(@PathVariable Long dividaId) {
        return ResponseEntity.ok(dividaService.buscarDividaPorId(dividaId));
    }
    
    @PostMapping
    public ResponseEntity<DividaDTO> registrarDivida(@RequestBody@Valid DividaForm form, UriComponentsBuilder uriBuilder) {
        var dto = dividaService.registrarDivida(form);
        return ResponseEntity.created(uriBuilder.path("divida/{dividaId}").buildAndExpand(dto.getId()).toUri()).body(dto);
    }
}
