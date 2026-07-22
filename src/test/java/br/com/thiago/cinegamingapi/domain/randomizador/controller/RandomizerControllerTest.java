package br.com.thiago.cinegamingapi.domain.randomizador.controller;

import br.com.thiago.cinegamingapi.domain.filme.CategoriaFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import br.com.thiago.cinegamingapi.domain.randomizador.usecase.RandomizerFilmeUseCase;
import br.com.thiago.cinegamingapi.domain.randomizador.usecase.RandomizerJogoUseCase;
import br.com.thiago.cinegamingapi.domain.usuario.Usuario;
import br.com.thiago.cinegamingapi.domain.usuario.UsuarioRepository;
import br.com.thiago.cinegamingapi.infra.seguranca.SecurityConfig;
import br.com.thiago.cinegamingapi.infra.seguranca.autenticacao.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(RandomizerController.class)
@Import(SecurityConfig.class)
class RandomizerControllerTest {


    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private RandomizerFilmeUseCase randomizerFilmeUseCase;
    @MockitoBean
    private RandomizerJogoUseCase randomizerJogoUseCase;

    @MockitoBean
    private UsuarioRepository usuarioRepository;
    @MockitoBean
    private TokenService tokenService;
    @MockitoBean
    private Usuario usuario;

    @Test
    @DisplayName("Deve garantir retornar o código 200 e o DTO para Filme aleatório")
    void garantirCodigo200DTOFilme(){
        var dadoDetalhamentoFilme = new DadosListagemFilme(1L,"TitutoTest1", CategoriaFilme.ACAO,"DescTest1");

        var tokenFalso = autenticacaoSimulada();

        Mockito.when(randomizerFilmeUseCase.buscarFilmeAleatorio()).thenReturn(dadoDetalhamentoFilme);

        assertThat(this.mvc.get()
                .uri("/random/filmes")
                .header("Authorization","Bearer " +tokenFalso))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemFilme.class)
                .isEqualTo(dadoDetalhamentoFilme);
    }
    @Test
    @DisplayName("Deve garantir retornar o código 200 e o DTO para Filme aleatório de Categoria")
    void garantirCodigo200DTOFilmeCategoria(){
        var dadoDetalhamentoFilme = new DadosListagemFilme(1L,"TitutoTest1", CategoriaFilme.ACAO,"DescTest1");
        var categoria = CategoriaFilme.ACAO;

        var tokenFalso = autenticacaoSimulada();

        Mockito.when(randomizerFilmeUseCase.buscarFilmeAleatorioPorCategoria(categoria)).thenReturn(dadoDetalhamentoFilme);

        assertThat(this.mvc.get()
                .uri("/random/filmes/categoria/{categoria}",categoria)
                .header("Authorization","Bearer " +tokenFalso))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemFilme.class)
                .isEqualTo(dadoDetalhamentoFilme);
    }

    @Test
    @DisplayName("Deve garantir retornar o código 200 e o DTO para Jogo aleatório")
    void garantirCodigo200DTOJogo(){
        var dadosDetalhamentoJogo = new DadosListagemJogo(1L,"TituloTest1","0000","DescTest1", CategoriaJogo.MMORPG);

        var tokenFalso = autenticacaoSimulada();

        Mockito.when(randomizerJogoUseCase.buscarJogoAleatorio()).thenReturn(dadosDetalhamentoJogo);

        assertThat(this.mvc.get()
                .uri("/random/jogos")
                .header("Authorization","Bearer " +tokenFalso))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemJogo.class)
                .isEqualTo(dadosDetalhamentoJogo);
    }

    @Test
    @DisplayName("Deve garantir retornar o código 200 e o DTO para Jogo aleatório de Categoria")
    void garantirCodigo200DTOJogoCategoria(){
        var dadosDetalhamentoJogo = new DadosListagemJogo(1L,"TituloTest1","0000","DescTest1", CategoriaJogo.MMORPG);
        var categoria = CategoriaJogo.MMORPG;

        var tokenFalso = autenticacaoSimulada();

        Mockito.when(randomizerJogoUseCase.buscarJogoAleatorioPorCategoria(categoria)).thenReturn(dadosDetalhamentoJogo);

        assertThat(this.mvc.get()
                .uri("/random/jogos/categoria/{categoria}",categoria)
                .header("Authorization","Bearer " +tokenFalso))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemJogo.class)
                .isEqualTo(dadosDetalhamentoJogo);
    }

    private String  autenticacaoSimulada(){
        var tokenFalso = "TokenFalso";
        var  email = "emailsimulado@email.com";
        Mockito.when(tokenService.verificarToken(tokenFalso)).thenReturn(email);
        Mockito.when(usuarioRepository.findByEmailIgnoreCaseAndAtivoTrue(email)).thenReturn(Optional.of(usuario));
        return tokenFalso;
    }
}