package br.com.thiago.cinegamingapi.domain.usuario.controller;

import br.com.thiago.cinegamingapi.domain.usuario.dto.*;
import br.com.thiago.cinegamingapi.domain.usuario.usecase.*;
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


    private final CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    private final ConsultarUsuarioUseCase consultarUsuarioUseCase;
    private final AtualizarNomeUsuarioUseCase atualizarNomeUsuarioUseCase;
    private final AtualizarEmailUsuarioUseCase atualizarEmailUsuarioUseCase;
    private final AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final ReativarUsuarioUseCase reativarUsuarioUseCase;



    public UsuarioController(CadastrarUsuarioUseCase cadastrarUsuarioUseCase, ConsultarUsuarioUseCase consultarUsuarioUseCase, AtualizarNomeUsuarioUseCase atualizarNomeUsuarioUseCase, AtualizarEmailUsuarioUseCase atualizarEmailUsuarioUseCase, AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase, DeletarUsuarioUseCase deletarUsuarioUseCase, ReativarUsuarioUseCase reativarUsuarioUseCase) {

        this.cadastrarUsuarioUseCase = cadastrarUsuarioUseCase;
        this.consultarUsuarioUseCase = consultarUsuarioUseCase;
        this.atualizarNomeUsuarioUseCase = atualizarNomeUsuarioUseCase;
        this.atualizarEmailUsuarioUseCase = atualizarEmailUsuarioUseCase;
        this.atualizarSenhaUsuarioUseCase = atualizarSenhaUsuarioUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.reativarUsuarioUseCase = reativarUsuarioUseCase;
    }


    @PostMapping
    public ResponseEntity<DadosListagemUsuario> cadastrar(@Valid @RequestBody DadosCadastroUsuario dados, UriComponentsBuilder builder){
        var usuario = cadastrarUsuarioUseCase.cadastrar(dados);
        URI uri = builder.path("/usuario/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuario>> listar(@PageableDefault(size = 20,sort = "nomeCompleto", direction = DESC) Pageable pageable){
        return ResponseEntity.ok(consultarUsuarioUseCase.listarUsuarios(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemUsuario> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(consultarUsuarioUseCase.buscarPorId(id));
    }


    @PatchMapping("/{id}/nome")
    public ResponseEntity<DadosListagemUsuario> atualizaNomePorId(@RequestBody @Valid DadosAtualizaNomeUsuario dados, @PathVariable Long id){
        return ResponseEntity.ok(atualizarNomeUsuarioUseCase.atualizaNomePorId(dados,id));
    }
    @PatchMapping("/{id}/email")
    public ResponseEntity<DadosListagemUsuario> atualizaEmailPorId(@RequestBody @Valid DadosAtualizaEmailUsuario dados, @PathVariable Long id){
        return ResponseEntity.ok(atualizarEmailUsuarioUseCase.atualizaEmailPorId(dados,id));
    }
    @PatchMapping("/{id}/senha")
    public ResponseEntity<DadosListagemUsuario> atualizaSenhaPorId(@RequestBody @Valid DadosAtualizaSenhaUsuario dados, @PathVariable Long id){
        return ResponseEntity.ok(atualizarSenhaUsuarioUseCase.atualizaSenhaPorId(dados,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id){
        deletarUsuarioUseCase.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reativar-conta/{id}")
    public ResponseEntity<DadosListagemUsuario> ativarPorId(@PathVariable Long id){
        var usuario = reativarUsuarioUseCase.reativarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }



}
