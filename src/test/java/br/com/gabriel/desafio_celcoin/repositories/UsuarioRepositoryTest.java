package br.com.gabriel.desafio_celcoin.repositories;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;
import static org.assertj.core.api.Assertions.assertThat;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.com.gabriel.desafio_celcoin.domain.entities.Usuario;
import br.com.gabriel.desafio_celcoin.domain.forms.UsuarioForm;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

@FlywayTest
@DataJpaTest
@AutoConfigureEmbeddedDatabase(refresh = AFTER_EACH_TEST_METHOD)
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
