package br.com.thiago.cinegamingapi.controller;

import br.com.thiago.cinegamingapi.domain.filme.Categoria;
import br.com.thiago.cinegamingapi.domain.filme.DadosListagemFilme;
import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.DadosListagemJogo;
import br.com.thiago.cinegamingapi.domain.randomizador.RandomizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/random")
public class RandomizerController {


    private final RandomizerService randomizerService;

    public RandomizerController(RandomizerService randomizerService) {
        this.randomizerService = randomizerService;
    }



    @GetMapping("/filmes")
    public ResponseEntity<DadosListagemFilme> buscarFilmeAleatorio(){
        return ResponseEntity.ok(randomizerService.buscarFilmeAleatorio());
    }

    @GetMapping("/filmes/categoria/{categoria}")
    public ResponseEntity<DadosListagemFilme> buscarFilmeAleatorioPorCategoria(@PathVariable Categoria categoria){
        return ResponseEntity.ok(randomizerService.buscarFilmeAleatorioPorCategoria(categoria));
    }

    @GetMapping("/jogos")
    public ResponseEntity<DadosListagemJogo> buscarJogoAleatorio(){
        return ResponseEntity.ok(randomizerService.buscarJogoAleatorio());
    }

    @GetMapping("/jogos/categoria/{categoria}")
    public ResponseEntity<DadosListagemJogo> buscarJogoAleatorioPorCategoria(@PathVariable CategoriaJogo categoria){
        return ResponseEntity.ok(randomizerService.buscarJogoAleatorioPorCategoria(categoria));
    }


}
