package br.com.thiago.cinegamingapi.domain.jogo.usecase;

import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.JogoRepository;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultarJogoUseCase {
    private final JogoRepository jogoRepository;

    public ConsultarJogoUseCase(JogoRepository jogoRepository) {
        this.jogoRepository = jogoRepository;
    }

    public Page<DadosListagemJogo> listar(Pageable page) {
        return jogoRepository.findAllByAtivoTrue(page).map(DadosListagemJogo::new);
    }

    public Page<DadosListagemJogo> listarPorCategoria(CategoriaJogo categoria, Pageable page) {
        return jogoRepository.findAllByCategoriaJogoAndAtivoTrue(categoria,page).map(DadosListagemJogo::new);
    }

    public Page<DadosListagemJogo> listarPorTitulo(String titulo, Pageable page) {
        return jogoRepository.findAllByTituloContainingIgnoreCaseAndAtivoTrue(titulo, page).map(DadosListagemJogo::new);
    }
}
