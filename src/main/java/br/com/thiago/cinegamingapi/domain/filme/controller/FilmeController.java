package br.com.thiago.cinegamingapi.domain.filme.controller;

import br.com.thiago.cinegamingapi.domain.filme.*;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosAtualizaFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosCadastroFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
import br.com.thiago.cinegamingapi.domain.filme.usecase.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/filme")
public class FilmeController {

    private final CadastrarFilmeUseCase cadastrarFilmeUseCase;
    private final ConsultarFilmeUseCase consultarFilmeUseCase;
    private final AtualizarFilmeUseCase atualizarFilmeUseCase;
    private final DesativarFilmeUseCase desativarFilmeUseCase;
    private final ReativarFilmeUseCase reativarFilmeUseCase;

    public FilmeController(CadastrarFilmeUseCase cadastrarFilmeUseCase, ConsultarFilmeUseCase consultarFilmeUseCase, AtualizarFilmeUseCase atualizarFilmeUseCase, DesativarFilmeUseCase desativarFilmeUseCase, ReativarFilmeUseCase reativarFilmeUseCase) {
        this.cadastrarFilmeUseCase = cadastrarFilmeUseCase;
        this.consultarFilmeUseCase = consultarFilmeUseCase;
        this.atualizarFilmeUseCase = atualizarFilmeUseCase;
        this.desativarFilmeUseCase = desativarFilmeUseCase;
        this.reativarFilmeUseCase = reativarFilmeUseCase;
    }

    @PostMapping
    public ResponseEntity<DadosListagemFilme> adicionarFilme(@Valid @RequestBody DadosCadastroFilme dados, UriComponentsBuilder uriComponentsBuilder){
        var filme = cadastrarFilmeUseCase.cadastraFilme(dados);
        var uri = uriComponentsBuilder.path("/filme/{id}").buildAndExpand(filme.id()).toUri();

        return ResponseEntity.created(uri).body(filme);
    }


    @GetMapping
    public ResponseEntity<Page<DadosListagemFilme>> listarfilmes(@PageableDefault(sort = "titulo") Pageable pageable){
        var filmes = consultarFilmeUseCase.listarFilmes(pageable);
        return ResponseEntity.ok(filmes);
    }


    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Page<DadosListagemFilme>> listarPorCategoria(@PathVariable CategoriaFilme categoria, @PageableDefault(sort = "titulo") Pageable pageable){
        var filmes = consultarFilmeUseCase.listarFilmesPorCategoria(categoria,pageable);
        return ResponseEntity.ok(filmes);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Page<DadosListagemFilme>> buscaPorTitulo(@PathVariable String titulo,@PageableDefault(sort = "titulo") Pageable pageable){
        var filme =consultarFilmeUseCase.buscaPorTitulo(titulo,pageable);
        return ResponseEntity.ok(filme);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<DadosListagemFilme> atualizarDados(@PathVariable Long id, @RequestBody DadosAtualizaFilme dados){
        return ResponseEntity.ok( atualizarFilmeUseCase.atualizaDados(id,dados));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        desativarFilmeUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reativar/{id}")
    public ResponseEntity<DadosListagemFilme> reativar(@PathVariable Long id){
        var filme = reativarFilmeUseCase.reativar(id);
        return ResponseEntity.ok(filme);
    }




}
