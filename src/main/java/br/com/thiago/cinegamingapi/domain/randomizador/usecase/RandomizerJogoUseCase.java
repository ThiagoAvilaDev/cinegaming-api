package br.com.thiago.cinegamingapi.domain.randomizador.usecase;

import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.JogoRepository;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import org.springframework.stereotype.Service;

import static br.com.thiago.cinegamingapi.domain.randomizador.Sorteador.sorteador;

@Service
public class RandomizerJogoUseCase {
    private final JogoRepository jogoRepository;

    public RandomizerJogoUseCase(JogoRepository jogoRepository) {
        this.jogoRepository = jogoRepository;
    }

    public DadosListagemJogo buscarJogoAleatorio(){
        var listJogo = jogoRepository.findAllByAtivoTrue();
        var position = sorteador(listJogo.size());
        return new DadosListagemJogo(listJogo.get(position));
    }

    public DadosListagemJogo buscarJogoAleatorioPorCategoria(CategoriaJogo categoria){
        var listJogo = jogoRepository.findAllByCategoriaJogoAndAtivoTrue(categoria);
        var position = sorteador(listJogo.size());
        return new DadosListagemJogo(listJogo.get(position));
    }
}
