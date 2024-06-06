package br.com.gabriel.desafio_celcoin.domain.dtos;

import br.com.gabriel.desafio_celcoin.domain.entities.Usuario;

public record UsuarioDTO(Long id, String email) {
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getEmail());
    }
}
