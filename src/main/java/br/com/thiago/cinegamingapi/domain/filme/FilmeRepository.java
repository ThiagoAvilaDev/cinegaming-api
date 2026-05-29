package br.com.thiago.cinegamingapi.domain.filme;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

    Page<Filme> findAllByCategoriaAndAtivoTrue(Categoria categoria, Pageable pageable);

    Page<Filme> findAllByAtivoTrue(Pageable pageable);

    Page<Filme> findByTituloContainingIgnoreCaseAndAtivoTrue(String titulo,Pageable pageable);

    List<Filme> findAllByAtivoTrue();

    List<Filme> findAllByCategoriaAndAtivoTrue(Categoria categoria);
}
