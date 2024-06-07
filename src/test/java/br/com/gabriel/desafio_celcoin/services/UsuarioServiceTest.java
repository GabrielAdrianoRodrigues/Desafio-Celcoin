package br.com.gabriel.desafio_celcoin.services;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.gabriel.desafio_celcoin.domain.dtos.UsuarioDTO;
import br.com.gabriel.desafio_celcoin.domain.entities.Usuario;
import br.com.gabriel.desafio_celcoin.domain.forms.UsuarioForm;
import br.com.gabriel.desafio_celcoin.repositories.UsuarioRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

@FlywayTest
@SpringBootTest
@AutoConfigureEmbeddedDatabase(refresh = AFTER_EACH_TEST_METHOD)
public class UsuarioServiceTest {
    
    @Autowired
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao enviar qualquer usuario caso nao exista no banco grava-lo")
    void registrarUsuarioCase1() {
        when(usuarioRepository.existUsuario(any())).thenReturn(false);

        usuarioService.registrarUsuario(new UsuarioForm("teste@test.com", "test"));

        verify(usuarioRepository, times(1)).save(any());
    }
    
    @Test
    @DisplayName("Ao enviar qualquer usuario caso exista retornar uma exececao")
    void registrarUsuariocase2() {
        when(usuarioRepository.existUsuario(any())).thenReturn(true);

        usuarioService.registrarUsuario(new UsuarioForm("string", "string"));

        DataIntegrityViolationException thrown = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioService.registrarUsuario(new UsuarioForm("teste@test.com", "test"));
        });

        Assertions.assertEquals("Usuario ja cadastrado", thrown.getMessage());
    }
    
    @Test
    @DisplayName("Ao solicitar um usuario por id retorna seu DTO objetivo testar mapeamento")
    void buscarPorIdCase1() {
        when(usuarioRepository.findById(1l)).thenReturn(Optional.of(new Usuario(1l, "teste@teste.com", any())));

        var serviceReturn = usuarioService.buscarPorId(1l);

        Assertions.assertEquals(new UsuarioDTO(1l, "teste@teste.com"), serviceReturn);
    }

}
