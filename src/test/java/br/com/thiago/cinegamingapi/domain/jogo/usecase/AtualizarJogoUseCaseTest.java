package br.com.thiago.cinegamingapi.domain.jogo.usecase;

import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.Jogo;
import br.com.thiago.cinegamingapi.domain.jogo.JogoRepository;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosAtualizaJogo;
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
class AtualizarJogoUseCaseTest {
    @Mock
    private JogoRepository jogoRepository;
    @InjectMocks
    private AtualizarJogoUseCase atualizarJogoUseCase;


    @Test
    @DisplayName("Deveria impedir atualizar Jogo se o ativo estiver false")
    void deveriaImpedirAtualizarAtivoFalse(){
        Long idExistente = 1L;
        var jogo = criarJogoAuxiliar();
        var dados = new DadosAtualizaJogo("TituloTest","0000","DescTest", CategoriaJogo.MMORPG);


        jogo.deletar();

        Mockito.when(jogoRepository.findById(idExistente)).thenReturn(Optional.of(jogo));

        var exception = Assertions.assertThrows(RegrasDeNegocioException.class, () -> atualizarJogoUseCase.atualizaDados(idExistente,dados));
        Assertions.assertEquals("A entidade informada não pode ser modificada.",exception.getMessage());
    }

    @Test
    @DisplayName("Deveria impedir Atualização de Id Inexistente")
    void deveriaImpedirAtualizarIdInexistente(){
        Long idInexistente = 1L;
        var dados = new DadosAtualizaJogo("TituloTest","0000","DescTest",CategoriaJogo.MMORPG);

        var exception = Assertions.assertThrows(RegrasDeNegocioException.class,() -> atualizarJogoUseCase.atualizaDados(idInexistente,dados));
        Assertions.assertEquals("O Id informado não existe", exception.getMessage());
    }

    private Jogo criarJogoAuxiliar(){
        var dadosJogo = new DadosCadastroJogo("TituloTest","0000","DescTest",CategoriaJogo.MMORPG);
        return new Jogo(dadosJogo);
    }
}