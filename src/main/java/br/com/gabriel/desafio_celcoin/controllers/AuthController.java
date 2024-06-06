package br.com.gabriel.desafio_celcoin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabriel.desafio_celcoin.domain.entities.Usuario;
import br.com.gabriel.desafio_celcoin.domain.forms.UsuarioForm;
import br.com.gabriel.desafio_celcoin.services.TokenService;
import jakarta.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("login")
    public ResponseEntity<String> authentication(@RequestBody@Valid UsuarioForm form) {
        final Usuario principal = (Usuario) manager.authenticate(new UsernamePasswordAuthenticationToken(form.email(), form.password())).getPrincipal();
        return ResponseEntity.ok("Bearer "+tokenService.tokenFactory(principal));
    }     
}
