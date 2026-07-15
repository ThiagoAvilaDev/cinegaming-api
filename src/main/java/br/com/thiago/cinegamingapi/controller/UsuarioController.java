package br.com.thiago.cinegamingapi.controller;

import br.com.thiago.cinegamingapi.domain.usuario.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping
    public ResponseEntity<DadosListagemUsuario> cadastrar(@Valid @RequestBody  DadosCadastroUsuario dados, UriComponentsBuilder builder){
        var usuario = usuarioService.cadastrar(dados);
        URI uri = builder.path("/usuario/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuario>> listar(@PageableDefault(size = 20,sort = "nomeCompleto", direction = DESC) Pageable pageable){
        return ResponseEntity.ok(usuarioService.listarUsuarios(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemUsuario> buscarPorId(@PathVariable Long id){
       return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }


    @PatchMapping("/{id}/nome")
    public ResponseEntity<DadosListagemUsuario> atualizaNomePorId(@RequestBody @Valid DadosAtualizaNomeUsuario dados, @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.atualizaNomePorId(dados,id));
    }
    @PatchMapping("/{id}/email")
    public ResponseEntity<DadosListagemUsuario> atualizaEmailPorId(@RequestBody @Valid DadosAtualizaEmailUsuario dados, @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.atualizaEmailPorId(dados,id));
    }
    @PatchMapping("/{id}/senha")
    public ResponseEntity<DadosListagemUsuario> atualizaSenhaPorId(@RequestBody @Valid DadosAtualizaSenhaUsuario dados, @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.atualizaSenhaPorId(dados,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id){
        usuarioService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reativar-conta/{id}")
    public ResponseEntity<DadosListagemUsuario> ativarPorId(@PathVariable Long id){
        var usuario = usuarioService.reativarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }



}
