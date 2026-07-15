package br.com.thiago.cinegamingapi.infra.seguranca.autenticacao;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;


    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<DadosToken> loginUsuario(@Valid @RequestBody DadosLogin dados){
       var dadosToken = autenticacaoService.autenticarLoginUsuario(dados);
       return ResponseEntity.ok(dadosToken);
    }


    @PostMapping("/atualizar-token")
    public ResponseEntity<DadosToken> atualizarRefreshToken(@Valid @RequestBody DadosRefreshToken dados){
        var tokenAtualizado = autenticacaoService.atualizarRefreshToken(dados.refreshToken());
        return ResponseEntity.ok(tokenAtualizado);
    }



}
