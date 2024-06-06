package br.com.gabriel.desafio_celcoin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.gabriel.desafio_celcoin.models.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);

    @Query(value ="SELECT EXISTS(SELECT FROM bu_usuarios WHERE email = :email)" , nativeQuery = true)
    Boolean existUsuario(@Param("email") String email);
}
