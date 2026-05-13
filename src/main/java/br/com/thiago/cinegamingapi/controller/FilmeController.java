package br.com.thiago.cinegamingapi.controller;

import br.com.thiago.cinegamingapi.domain.filme.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/filme")
public class FilmeController {


    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @PostMapping
    public ResponseEntity<DadosListagemFilme> adicionarFilme(@RequestBody  DadosCadastroFilme dados, UriComponentsBuilder uriComponentsBuilder){
        var filme = filmeService.adicionarFilme(dados);
        var uri = uriComponentsBuilder.path("/filme/{id}").buildAndExpand(filme.id()).toUri();

        return ResponseEntity.created(uri).body(filme);
    }


    @GetMapping
    public ResponseEntity<Page<DadosListagemFilme>> listarfilmes(@PageableDefault(sort = "titulo") Pageable pageable){
        var filmes = filmeService.listarFilmes(pageable);
        return ResponseEntity.ok(filmes);
    }


    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Page<DadosListagemFilme>> listarPorCategoria(@PathVariable Categoria  categoria,@PageableDefault(sort = "titulo") Pageable pageable){
        var filmes = filmeService.listarFilmesPorCategoria(categoria,pageable);
        return ResponseEntity.ok(filmes);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Page<DadosListagemFilme>> buscaPorTitulo(@PathVariable String titulo,@PageableDefault(sort = "titulo") Pageable pageable){
        var filme =filmeService.buscaPorTitulo(titulo,pageable);
        return ResponseEntity.ok(filme);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<DadosListagemFilme> atualizarDados(@PathVariable Long id, @RequestBody  DadosAtualizaFilme dados){
       return ResponseEntity.ok( filmeService.atualizaDados(id,dados));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        filmeService.excluir(id);
        return ResponseEntity.noContent().build();
    }





}
