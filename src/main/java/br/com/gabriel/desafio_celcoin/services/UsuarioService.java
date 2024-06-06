package br.com.gabriel.desafio_celcoin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabriel.desafio_celcoin.models.dtos.UsuarioDTO;
import br.com.gabriel.desafio_celcoin.models.entities.Usuario;
import br.com.gabriel.desafio_celcoin.models.forms.UsuarioForm;
import br.com.gabriel.desafio_celcoin.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO registrarUsuario(@Valid UsuarioForm form) {
        if(usuarioRepository.existUsuario(form.email())) {
            return null; 
        }
        return new UsuarioDTO(usuarioRepository.save(new Usuario(form)));      
    }

    public UsuarioDTO buscarPorId(Long id) {
        return new UsuarioDTO(usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }
    
}
