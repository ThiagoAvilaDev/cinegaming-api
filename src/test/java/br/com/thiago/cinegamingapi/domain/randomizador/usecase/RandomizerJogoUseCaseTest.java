package br.com.thiago.cinegamingapi.domain.randomizador.usecase;

import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.Jogo;
import br.com.thiago.cinegamingapi.domain.jogo.JogoRepository;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosCadastroJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import br.com.thiago.cinegamingapi.domain.randomizador.Sorteador;
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
class RandomizerJogoUseCaseTest {

    @Mock
    private JogoRepository jogoRepository;
    @InjectMocks
    private RandomizerJogoUseCase randomizerJogoUseCase;

    @Test
    @DisplayName("Deve Garantir que Retorne um Jogo aleatório")
    void garantirJogoAleatorio(){
        var jogo1 = new Jogo(new DadosCadastroJogo("TituloTest1","0000","DescTest1", CategoriaJogo.MMORPG));
        var jogo2 =  new Jogo(new DadosCadastroJogo("TituloTest2","0000","DesctTest2", CategoriaJogo.MMORPG));
        List<Jogo> listaJogoFicticios = List.of(jogo1,jogo2);
        var jogoRetorno = new DadosListagemJogo(jogo2.getId(),jogo2.getTitulo(),jogo2.getAnoLancamento(),jogo2.getDescricao(),jogo2.getCategoriaJogo());

        Mockito.when(jogoRepository.findAllByAtivoTrue()).thenReturn(listaJogoFicticios);
        try (var mockSorteador =  Mockito.mockStatic(Sorteador.class)){
            mockSorteador.when(() -> Sorteador.sorteador(listaJogoFicticios.size())).thenReturn(1);
            var resultado = randomizerJogoUseCase.buscarJogoAleatorio();

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
            var resultado = randomizerJogoUseCase.buscarJogoAleatorioPorCategoria(CategoriaJogo.MMORPG);

            Assertions.assertEquals(jogoRetorno, resultado);

        }

    }

}