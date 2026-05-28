package br.com.thiago.cinegamingapi.controller;

import br.com.thiago.cinegamingapi.domain.filme.Categoria;
import br.com.thiago.cinegamingapi.domain.filme.DadosListagemFilme;
import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.DadosListagemJogo;
import br.com.thiago.cinegamingapi.domain.randomizador.RandomizerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(RandomizerController.class)
class RandomizerControllerTest {


    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private RandomizerService randomizerService;

    @Test
    @DisplayName("Deve garantir retornar o código 200 e o DTO para Filme aleatório")
    void garantirCodigo200DTOFilme(){
        var dadoDetalhamentoFilme = new DadosListagemFilme(1L,"TitutoTest1", Categoria.ACAO,"DescTest1");

        Mockito.when(randomizerService.buscarFilmeAleatorio()).thenReturn(dadoDetalhamentoFilme);

        assertThat(this.mvc.get()
                .uri("/random/filmes"))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemFilme.class)
                .isEqualTo(dadoDetalhamentoFilme);
    }
    @Test
    @DisplayName("Deve garantir retornar o código 200 e o DTO para Filme aleatório de Categoria")
    void garantirCodigo200DTOFilmeCategoria(){
        var dadoDetalhamentoFilme = new DadosListagemFilme(1L,"TitutoTest1", Categoria.ACAO,"DescTest1");
        var categoria = Categoria.ACAO;
        Mockito.when(randomizerService.buscarFilmeAleatorioPorCategoria(categoria)).thenReturn(dadoDetalhamentoFilme);

        assertThat(this.mvc.get()
                .uri("/random/filmes/categoria/{categoria}",categoria))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemFilme.class)
                .isEqualTo(dadoDetalhamentoFilme);
    }

    @Test
    @DisplayName("Deve garantir retornar o código 200 e o DTO para Jogo aleatório")
    void garantirCodigo200DTOJogo(){
        var dadosDetalhamentoJogo = new DadosListagemJogo(1L,"TituloTest1","0000","DescTest1", CategoriaJogo.MMORPG);

        Mockito.when(randomizerService.buscarJogoAleatorio()).thenReturn(dadosDetalhamentoJogo);

        assertThat(this.mvc.get()
                .uri("/random/jogos"))
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

        Mockito.when(randomizerService.buscarJogoAleatorioPorCategoria(categoria)).thenReturn(dadosDetalhamentoJogo);

        assertThat(this.mvc.get()
                .uri("/random/jogos/categoria/{categoria}",categoria))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemJogo.class)
                .isEqualTo(dadosDetalhamentoJogo);
    }
}