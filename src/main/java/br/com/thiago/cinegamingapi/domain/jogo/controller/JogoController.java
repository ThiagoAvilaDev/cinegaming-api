package br.com.thiago.cinegamingapi.domain.jogo.controller;

import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosAtualizaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosCadastroJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import br.com.thiago.cinegamingapi.domain.jogo.usecase.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/jogo")
public class JogoController {

    private final CadastrarJogoUseCase cadastrarJogoUseCase;
    private final ConsultarJogoUseCase consultarJogoUseCase;
    private final AtualizarJogoUseCase atualizarJogoUseCase;
    private final DesativarJogoUseCase desativarJogoUseCase;
    private final ReativarJogoUseCase reativarJogoUseCase;

    public JogoController(CadastrarJogoUseCase cadastrarJogoUseCase, ConsultarJogoUseCase consultarJogoUseCase, AtualizarJogoUseCase atualizarJogoUseCase, DesativarJogoUseCase desativarJogoUseCase, ReativarJogoUseCase reativarJogoUseCase) {
        this.cadastrarJogoUseCase = cadastrarJogoUseCase;
        this.consultarJogoUseCase = consultarJogoUseCase;
        this.atualizarJogoUseCase = atualizarJogoUseCase;
        this.desativarJogoUseCase = desativarJogoUseCase;
        this.reativarJogoUseCase = reativarJogoUseCase;
    }

    @PostMapping
    public ResponseEntity<DadosListagemJogo> cadastrarJogo(@RequestBody @Valid DadosCadastroJogo dados, UriComponentsBuilder builder){
        var jogo = cadastrarJogoUseCase.cadastrar(dados);
        var uri = builder.path("/jogo/{id}").buildAndExpand(jogo.id()).toUri();
        return ResponseEntity.created(uri).body(jogo);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemJogo>> listar(@PageableDefault(size = 20,sort = "titulo") Pageable page){
        var listJogo = consultarJogoUseCase.listar(page);
        return ResponseEntity.ok(listJogo);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Page<DadosListagemJogo>> buscarPorCategoria(@PathVariable CategoriaJogo categoria, @PageableDefault(size = 20,sort = "titulo") Pageable page){
        var listJogo = consultarJogoUseCase.listarPorCategoria(categoria,page);
        return ResponseEntity.ok(listJogo);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Page<DadosListagemJogo>> buscarPorTitulo(@PathVariable String titulo, @PageableDefault(size = 20,sort = "titulo") Pageable page){
        var listJogo = consultarJogoUseCase.listarPorTitulo(titulo,page);
        return ResponseEntity.ok(listJogo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DadosListagemJogo> atualizarDados(@PathVariable Long id, @RequestBody DadosAtualizaJogo dados){
        var jogo =  atualizarJogoUseCase.atualizaDados(id,dados);
        return ResponseEntity.ok(jogo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        desativarJogoUseCase.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reativar/{id}")
    public ResponseEntity<DadosListagemJogo> reativar(@PathVariable Long id){
        var jogo = reativarJogoUseCase.reativar(id);
        return ResponseEntity.ok(jogo);
    }



}
