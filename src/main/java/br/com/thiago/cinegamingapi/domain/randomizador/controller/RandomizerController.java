package br.com.thiago.cinegamingapi.domain.randomizador.controller;

import br.com.thiago.cinegamingapi.domain.filme.CategoriaFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import br.com.thiago.cinegamingapi.domain.randomizador.usecase.RandomizerFilmeUseCase;
import br.com.thiago.cinegamingapi.domain.randomizador.usecase.RandomizerJogoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/random")
public class RandomizerController {



    private final RandomizerFilmeUseCase randomizerFilmeUseCase;
    private final RandomizerJogoUseCase randomizerJogoUseCase;

    public RandomizerController(RandomizerFilmeUseCase randomizerFilmeUseCase, RandomizerJogoUseCase randomizerJogoUseCase) {
        this.randomizerFilmeUseCase = randomizerFilmeUseCase;
        this.randomizerJogoUseCase = randomizerJogoUseCase;
    }



    @GetMapping("/filmes")
    public ResponseEntity<DadosListagemFilme> buscarFilmeAleatorio(){
        return ResponseEntity.ok(randomizerFilmeUseCase.buscarFilmeAleatorio());
    }

    @GetMapping("/filmes/categoria/{categoria}")
    public ResponseEntity<DadosListagemFilme> buscarFilmeAleatorioPorCategoria(@PathVariable CategoriaFilme categoria){
        return ResponseEntity.ok(randomizerFilmeUseCase.buscarFilmeAleatorioPorCategoria(categoria));
    }

    @GetMapping("/jogos")
    public ResponseEntity<DadosListagemJogo> buscarJogoAleatorio(){
        return ResponseEntity.ok(randomizerJogoUseCase.buscarJogoAleatorio());
    }

    @GetMapping("/jogos/categoria/{categoria}")
    public ResponseEntity<DadosListagemJogo> buscarJogoAleatorioPorCategoria(@PathVariable CategoriaJogo categoria){
        return ResponseEntity.ok(randomizerJogoUseCase.buscarJogoAleatorioPorCategoria(categoria));
    }


}
