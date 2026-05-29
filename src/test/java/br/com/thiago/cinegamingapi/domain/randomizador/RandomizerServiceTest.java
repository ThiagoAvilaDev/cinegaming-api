package br.com.thiago.cinegamingapi.domain.randomizador;

import br.com.thiago.cinegamingapi.domain.filme.*;
import br.com.thiago.cinegamingapi.domain.jogo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class RandomizerServiceTest {
    @Mock
    private FilmeRepository filmeRepository;
    @Mock
    private JogoRepository jogoRepository;

    @InjectMocks
    private RandomizerService randomizerService;


    @Test
    @DisplayName("Deve Garantir que Retorne um Filme aleatório")
    void garantirFilmeAleatorio(){
        var filme1 = new Filme(new DadosCadastroFilme("TituloTest1","DescTest1", CategoriaFilme.ACAO));
        var filme2 =  new Filme(new DadosCadastroFilme("TituloTest2","DescTest2", CategoriaFilme.ACAO));
        List<Filme> listaFilmeFicticios = List.of(filme1,filme2);
        var filmeRetorno = new DadosListagemFilme(filme2.getId(),filme2.getTitulo(),filme2.getCategoria(),filme2.getDescricao());

        Mockito.when(filmeRepository.findAllByAtivoTrue()).thenReturn(listaFilmeFicticios);
        try (var mockSorteador =  Mockito.mockStatic(Sorteador.class)){
            mockSorteador.when(() -> Sorteador.sorteador(listaFilmeFicticios.size())).thenReturn(1);
            var resultado = randomizerService.buscarFilmeAleatorio();

            Assertions.assertEquals(filmeRetorno, resultado);

        }

    }
    @Test
    @DisplayName("Deve Garantir que Retorne um Filme aleatório da categoria Escolhida")
    void garantirFilmeAleatorioCategoria(){
        var filme1 = new Filme(new DadosCadastroFilme("TituloTest1","DescTest1", CategoriaFilme.ACAO));
        var filme2 =  new Filme(new DadosCadastroFilme("TituloTest2","DescTest2", CategoriaFilme.ACAO));
        List<Filme> listaFilmeFicticios = List.of(filme1,filme2);
        var filmeRetorno = new DadosListagemFilme(filme2.getId(),filme2.getTitulo(),filme2.getCategoria(),filme2.getDescricao());

        Mockito.when(filmeRepository.findAllByCategoriaAndAtivoTrue(CategoriaFilme.ACAO)).thenReturn(listaFilmeFicticios);
        try (var mockSorteador =  Mockito.mockStatic(Sorteador.class)){
            mockSorteador.when(() -> Sorteador.sorteador(listaFilmeFicticios.size())).thenReturn(1);
            var resultado = randomizerService.buscarFilmeAleatorioPorCategoria(CategoriaFilme.ACAO);
            Assertions.assertEquals(filmeRetorno, resultado);
        }

    }

    @Test
    @DisplayName("Deve Garantir que Retorne um Jogo aleatório")
    void garantirJogoAleatorio(){
        var jogo1 = new Jogo(new DadosCadastroJogo("TituloTest1","0000","DescTest1",CategoriaJogo.MMORPG));
        var jogo2 =  new Jogo(new DadosCadastroJogo("TituloTest2","0000","DesctTest2", CategoriaJogo.MMORPG));
        List<Jogo> listaJogoFicticios = List.of(jogo1,jogo2);
        var jogoRetorno = new DadosListagemJogo(jogo2.getId(),jogo2.getTitulo(),jogo2.getAnoLancamento(),jogo2.getDescricao(),jogo2.getCategoriaJogo());

        Mockito.when(jogoRepository.findAllByAtivoTrue()).thenReturn(listaJogoFicticios);
        try (var mockSorteador =  Mockito.mockStatic(Sorteador.class)){
            mockSorteador.when(() -> Sorteador.sorteador(listaJogoFicticios.size())).thenReturn(1);
            var resultado = randomizerService.buscarJogoAleatorio();

            Assertions.assertEquals(jogoRetorno, resultado);

        }

    }
    @Test
    @DisplayName("Deve Garantir que Retorne um Jogo aleatório da categoria escolhida")
    void garantirJogoAleatorioCategoria(){
        var jogo1 = new Jogo(new DadosCadastroJogo("TituloTest1","0000","DescTest1",CategoriaJogo.MMORPG));
        var jogo2 =  new Jogo(new DadosCadastroJogo("TituloTest2","0000","DesctTest2", CategoriaJogo.MMORPG));
        List<Jogo> listaJogoFicticios = List.of(jogo1,jogo2);
        var jogoRetorno = new DadosListagemJogo(jogo2.getId(),jogo2.getTitulo(),jogo2.getAnoLancamento(),jogo2.getDescricao(),jogo2.getCategoriaJogo());

        Mockito.when(jogoRepository.findAllByCategoriaJogoAndAtivoTrue(CategoriaJogo.MMORPG)).thenReturn(listaJogoFicticios);
        try (var mockSorteador =  Mockito.mockStatic(Sorteador.class)){
            mockSorteador.when(() -> Sorteador.sorteador(listaJogoFicticios.size())).thenReturn(1);
            var resultado = randomizerService.buscarJogoAleatorioPorCategoria(CategoriaJogo.MMORPG);

            Assertions.assertEquals(jogoRetorno, resultado);

        }

    }


}