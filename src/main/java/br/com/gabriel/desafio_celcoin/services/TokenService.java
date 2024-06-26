package br.com.gabriel.desafio_celcoin.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import br.com.gabriel.desafio_celcoin.domain.entities.Usuario;

@Service
public class TokenService {

    @Value("${token.secret}")
    private String secret;

    public String tokenFactory(Usuario authUser) {
        try {
            return JWT.create()
                .withIssuer("DesafioCelcoin")
                .withSubject(authUser.getUsername())
                .withExpiresAt(expiresAt())
                .sign(Algorithm.HMAC256(secret));
        } catch (JWTCreationException exception) {
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("DesafioCelcoin")
                .build()
                .verify(tokenJWT)
            .getSubject();
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Token expirado ou invalido", exception);
        }
    }

    private Instant expiresAt() {
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-03:00"));
    }
}