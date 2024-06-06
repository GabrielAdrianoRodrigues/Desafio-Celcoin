package br.com.gabriel.desafio_celcoin.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import br.com.gabriel.desafio_celcoin.EmbeddedPostgres.EmbeddedPostgresConfiguration.EmbeddedPostgresExtension;
import br.com.gabriel.desafio_celcoin.EmbeddedPostgres.EmbeddedPostgresWithFlywayConfiguration;
import br.com.gabriel.desafio_celcoin.domain.entities.Usuario;
import br.com.gabriel.desafio_celcoin.domain.forms.UsuarioForm;

@DataJpaTest
@ExtendWith(EmbeddedPostgresExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { EmbeddedPostgresWithFlywayConfiguration.class })
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager em;
    
    @Test
    @DisplayName("Ao verificar a existencia do usuario caso exista na base retorna true")
    void existUsuarioCase1() {
        var usuarioTeste = createUsuario(new UsuarioForm("teste@test.com", "test"));

        assertThat(usuarioRepository.existUsuario(usuarioTeste.getEmail())).isTrue();
    }

    @Test
    @DisplayName("Ao verificar a existencia do usuario caso nao exista na base retorna false")
    void existUsuarioCase2() {
        createUsuario(new UsuarioForm("teste@test.com", "test"));
        var usuarioTeste = createUsuario(new UsuarioForm("teste1@test.com", "test"));

        assertThat(usuarioRepository.existUsuario(usuarioTeste.getEmail())).isTrue();
    }

    private Usuario createUsuario(UsuarioForm form) {
        var usuario = new Usuario(form);
        em.persist(usuario);
        return usuario;
    }
}
