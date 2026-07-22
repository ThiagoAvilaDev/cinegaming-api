package br.com.thiago.cinegamingapi.domain.jogo.usecase;

import br.com.thiago.cinegamingapi.domain.jogo.Jogo;
import br.com.thiago.cinegamingapi.domain.jogo.JogoRepository;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosCadastroJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo.AVENTURA;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CadastrarJogoUseCaseTest {

    @Mock
    private JogoRepository jogoRepository;
    @InjectMocks
    private CadastrarJogoUseCase cadastrarJogoUseCase;



    @Test
    @DisplayName("Dado jogo com todos os campos quando salvo no repository então deveria retornar DTO ")
    void cenario1(){
        var dtoJogo = new DadosCadastroJogo("Jogo Titulo","2020","Descrição jogo", AVENTURA);
        var novoJogo = new Jogo(dtoJogo);

        Mockito.when(jogoRepository.save(Mockito.any(Jogo.class))).thenReturn(novoJogo);

        assertEquals(cadastrarJogoUseCase.cadastrar(dtoJogo),new DadosListagemJogo(novoJogo));

    }
}