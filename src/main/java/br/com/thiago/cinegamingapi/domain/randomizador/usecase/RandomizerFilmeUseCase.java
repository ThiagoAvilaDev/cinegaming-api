package br.com.thiago.cinegamingapi.domain.randomizador.usecase;

import br.com.thiago.cinegamingapi.domain.filme.CategoriaFilme;
import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
import org.springframework.stereotype.Service;

import static br.com.thiago.cinegamingapi.domain.randomizador.Sorteador.sorteador;

@Service
public class RandomizerFilmeUseCase {

    private final FilmeRepository filmeRepository;

    public RandomizerFilmeUseCase(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public DadosListagemFilme buscarFilmeAleatorio(){
        var listFilms = filmeRepository.findAllByAtivoTrue();
        var position = sorteador(listFilms.size());
        return new DadosListagemFilme(listFilms.get(position));
    }

    public DadosListagemFilme buscarFilmeAleatorioPorCategoria(CategoriaFilme categoria){
        var listFilms = filmeRepository.findAllByCategoriaAndAtivoTrue(categoria);
        var position = sorteador(listFilms.size());
        return new DadosListagemFilme(listFilms.get(position));
    }
}
