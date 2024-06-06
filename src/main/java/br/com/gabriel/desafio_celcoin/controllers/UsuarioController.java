package br.com.gabriel.desafio_celcoin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.gabriel.desafio_celcoin.domain.dtos.UsuarioDTO;
import br.com.gabriel.desafio_celcoin.domain.forms.UsuarioForm;
import br.com.gabriel.desafio_celcoin.services.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("usuario")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("{usuarioId}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable("usuarioId") Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody@Valid UsuarioForm form, UriComponentsBuilder uriBuilder) {
        var dto = usuarioService.registrarUsuario(form);
        return ResponseEntity.created(uriBuilder.path("usuario/{usuarioId}").buildAndExpand(dto.id()).toUri()).body(dto);
    }
}
