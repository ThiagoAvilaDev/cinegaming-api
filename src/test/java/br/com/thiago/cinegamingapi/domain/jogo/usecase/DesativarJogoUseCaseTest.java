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
class DesativarJogoUseCaseTest {
    @Mock
    private JogoRepository jogoRepository;
    @InjectMocks
    private DesativarJogoUseCase desativarJogoUseCase;

    @Test
    @DisplayName("Exclusão Lógica deve tornar ativo em false")
    void garantirExclusaoLogica(){
        Long idExistente = 1L;
        var jogo = criarJogoAuxiliar();

        Mockito.when(jogoRepository.findById(idExistente)).thenReturn(Optional.of(jogo));
        desativarJogoUseCase.desativar(idExistente);

        Assertions.assertFalse(jogo.getAtivo());
    }

    @Test
    @DisplayName("Deve lançar Exceção ao tentar Desativar jogo já desativo.")
    void deveLancarExcecaoJogoDesativado(){
        Long idExistente = 1L;
        var jogo = criarJogoAuxiliar();

        Mockito.when(jogoRepository.findById(idExistente)).thenReturn(Optional.of(jogo));
        jogo.deletar();

        var exception = Assertions.assertThrows(RegrasDeNegocioException.class, () -> desativarJogoUseCase.desativar(idExistente));
        Assertions.assertEquals("Este Jogo já está desativado.",exception.getMessage());
    }

    private Jogo criarJogoAuxiliar(){
        var dadosJogo = new DadosCadastroJogo("TituloTest","0000","DescTest", CategoriaJogo.MMORPG);
        return new Jogo(dadosJogo);
    }
}