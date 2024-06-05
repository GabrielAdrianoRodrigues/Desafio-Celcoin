package br.com.gabriel.desafio_celcoin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabriel.desafio_celcoin.models.entities.Usuario;
import br.com.gabriel.desafio_celcoin.models.forms.LoginForm;
import br.com.gabriel.desafio_celcoin.services.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody@Valid LoginForm form) {
        final Usuario principal = (Usuario) manager.authenticate(new UsernamePasswordAuthenticationToken(form.email(), form.password())).getPrincipal();
        return ResponseEntity.ok(tokenService.tokenFactory(principal));
    }
}
