package br.com.thiago.cinegamingapi.domain.jogo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public interface JogoRepository extends JpaRepository<Jogo,Long> {


    Page<Jogo> findAllByAtivoTrue(Pageable page);

    Page<Jogo> findAllByCategoriaJogoAndAtivoTrue(CategoriaJogo categoria, Pageable page);

    Page<Jogo> findAllByTituloContainingIgnoreCaseAndAtivoTrue(String titulo, Pageable page);
}
