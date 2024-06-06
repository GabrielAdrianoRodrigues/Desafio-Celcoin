package br.com.gabriel.desafio_celcoin.models.dtos;

import br.com.gabriel.desafio_celcoin.models.entities.Usuario;

public record UsuarioDTO(Long id, String email) {
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getEmail());
    }
}
