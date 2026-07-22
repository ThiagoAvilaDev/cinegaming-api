package br.com.thiago.cinegamingapi.domain.randomizador.usecase;

import br.com.thiago.cinegamingapi.domain.filme.CategoriaFilme;
import br.com.thiago.cinegamingapi.domain.filme.Filme;
import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosCadastroFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
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
class RandomizerFilmeUseCaseTest {

    @Mock
    private FilmeRepository filmeRepository;
    @InjectMocks
    private RandomizerFilmeUseCase randomizerFilmeUseCase;

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
            var resultado = randomizerFilmeUseCase.buscarFilmeAleatorio();

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
            var resultado = randomizerFilmeUseCase.buscarFilmeAleatorioPorCategoria(CategoriaFilme.ACAO);
            Assertions.assertEquals(filmeRetorno, resultado);
        }

    }

}