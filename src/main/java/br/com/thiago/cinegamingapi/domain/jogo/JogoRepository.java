package br.com.thiago.cinegamingapi.domain.jogo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogoRepository extends JpaRepository<Jogo,Long> {


    Page<Jogo> findAllByAtivoTrue(Pageable page);

    Page<Jogo> findAllByCategoriaJogoAndAtivoTrue(CategoriaJogo categoria, Pageable page);

    Page<Jogo> findAllByTituloContainingIgnoreCaseAndAtivoTrue(String titulo, Pageable page);

    List<Jogo> findAllByAtivoTrue();

    List<Jogo> findAllByCategoriaJogoAndAtivoTrue(CategoriaJogo categoria);
}
