package br.com.thiago.cinegamingapi.domain.jogo.usecase;

import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.Jogo;
import br.com.thiago.cinegamingapi.domain.jogo.JogoRepository;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosCadastroJogo;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReativarJogoUseCaseTest {
    @Mock
    private JogoRepository jogoRepository;
    @InjectMocks
    private ReativarJogoUseCase reativarJogoUseCase;
    @Test
    @DisplayName("Reativar deve tornar ativo em true")
    void garantirReativarEntidadeCorretamente(){
        Long idexistente = 1L;
        var jogo = criarJogoAuxiliar();

        jogo.deletar();
        Mockito.when(jogoRepository.findById(idexistente)).thenReturn(Optional.of(jogo));
        reativarJogoUseCase.reativar(idexistente);

        Assertions.assertTrue(jogo.getAtivo());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar ativar jogo já ativo")
    void deveLancarExcecaoJogoAtivo(){
        Long idExistente = 1L;
        var jogo = criarJogoAuxiliar();

        Mockito.when(jogoRepository.findById(idExistente)).thenReturn(Optional.of(jogo));


        var exception = Assertions.assertThrows(RegrasDeNegocioException.class,() -> reativarJogoUseCase.reativar(idExistente));
        Assertions.assertEquals("Este Jogo já está ativo.",exception.getMessage());
    }



    private Jogo criarJogoAuxiliar(){
        var dadosJogo = new DadosCadastroJogo("TituloTest","0000","DescTest", CategoriaJogo.MMORPG);
        return new Jogo(dadosJogo);
    }

}