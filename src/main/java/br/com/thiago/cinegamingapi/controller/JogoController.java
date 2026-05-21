package br.com.thiago.cinegamingapi.controller;

import br.com.thiago.cinegamingapi.domain.jogo.*;
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
    private  final JogoService jogoService;

    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    @PostMapping
    public ResponseEntity<DadosListagemJogo> cadastrarJogo(@RequestBody @Valid DadosCadastroJogo dados, UriComponentsBuilder builder){
        var jogo = jogoService.cadastrar(dados);
        var uri = builder.path("/jogo/{id}").buildAndExpand(jogo.id()).toUri();
        return ResponseEntity.created(uri).body(jogo);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemJogo>> listar(@PageableDefault(size = 10,sort = "titulo") Pageable page){
        var listJogo = jogoService.listar(page);
        return ResponseEntity.ok(listJogo);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Page<DadosListagemJogo>> buscarPorCategoria(@PathVariable CategoriaJogo categoria, @PageableDefault(size = 10,sort = "titulo") Pageable page){
        var listJogo = jogoService.listarPorCategoria(categoria,page);
        return ResponseEntity.ok(listJogo);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Page<DadosListagemJogo>> buscarPorTitulo(@PathVariable String titulo, @PageableDefault(size = 10,sort = "titulo") Pageable page){
        var listJogo = jogoService.listarPorTitulo(titulo,page);
        return ResponseEntity.ok(listJogo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DadosListagemJogo> atualizarDados(@PathVariable Long id, @RequestBody DadosAtualizaJogo dados){
      var jogo = jogoService.atualizaDados(id,dados);
      return ResponseEntity.ok(jogo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        jogoService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reativar/{id}")
    public ResponseEntity<DadosListagemJogo> reativar(@PathVariable Long id){
        var jogo = jogoService.reativar(id);
        return ResponseEntity.ok(jogo);
    }



}
