package br.com.thiago.cinegamingapi.domain.randomizador;

import br.com.thiago.cinegamingapi.domain.filme.Categoria;
import br.com.thiago.cinegamingapi.domain.filme.DadosListagemFilme;
import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.DadosListagemJogo;
import br.com.thiago.cinegamingapi.domain.jogo.JogoRepository;
import org.springframework.stereotype.Service;

import static br.com.thiago.cinegamingapi.domain.randomizador.Sorteador.sorteador;

@Service
public class RandomizerService {

    private final FilmeRepository filmeRepository;
    private final JogoRepository jogoRepository;


    public RandomizerService(FilmeRepository filmeRepository, JogoRepository jogoRepository) {
        this.filmeRepository = filmeRepository;
        this.jogoRepository = jogoRepository;
    }



    public DadosListagemFilme buscarFilmeAleatorio(){
        var listFilms = filmeRepository.findAllByAtivoTrue();
        var position = sorteador(listFilms.size());
        return new DadosListagemFilme(listFilms.get(position));
    }

    public DadosListagemFilme buscarFilmeAleatorioPorCategoria(Categoria categoria){
        var listFilms = filmeRepository.findByCategoriaAndAtivoTrue(categoria);
        var position = sorteador(listFilms.size());
        return new DadosListagemFilme(listFilms.get(position));
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
