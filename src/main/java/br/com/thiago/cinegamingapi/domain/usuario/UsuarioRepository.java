package br.com.thiago.cinegamingapi.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


    Page<Usuario> findAllByAtivoTrue(Pageable pageable);

    Optional<Usuario> findByEmailIgnoreCaseAndAtivoTrue(String email);
}
