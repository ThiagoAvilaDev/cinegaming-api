package br.com.thiago.cinegamingapi.domain.jogo;


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
class JogoServiceTest {

    @Mock
    private JogoRepository jogoRepository;
    @InjectMocks
    private JogoService jogoService;


    @Test
    @DisplayName("Deveria impedir atualizar Jogo se o ativo estiver false")
    void deveriaImpedirAtualizarAtivoFalse(){
        Long idExistente = 1L;
        var jogo = criarJogoAuxiliar();
        var dados = new DadosAtualizaJogo("TituloTest","0000","DescTest",CategoriaJogo.MMORPG);


        jogo.deletar();

        Mockito.when(jogoRepository.findById(idExistente)).thenReturn(Optional.of(jogo));

        var exception = Assertions.assertThrows(RegrasDeNegocioException.class, () -> jogoService.atualizaDados(idExistente,dados));
        Assertions.assertEquals("A entidade informada não pode ser modificada.",exception.getMessage());
    }

    @Test
    @DisplayName("Deveria impedir Atualização de Id Inexistente")
    void deveriaImpedirAtualizarIdInexistente(){
        Long idInexistente = 1L;
        var jogo = criarJogoAuxiliar();
        var dados = new DadosAtualizaJogo("TituloTest","0000","DescTest",CategoriaJogo.MMORPG);

        var exception = Assertions.assertThrows(RegrasDeNegocioException.class,() -> jogoService.atualizaDados(idInexistente,dados));
        Assertions.assertEquals("O Id informado não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Exclusão Lógica deve tornar ativo em false")
    void garantirExclusaoLogica(){
        Long idExistente = 1L;
        var jogo = criarJogoAuxiliar();

        Mockito.when(jogoRepository.findById(idExistente)).thenReturn(Optional.of(jogo));
        jogoService.desativar(idExistente);

        Assertions.assertFalse(jogo.getAtivo());
    }

    @Test
    @DisplayName("Deve lançar Exceção ao tentar Desativar jogo já desativo.")
    void deveLancarExcecaoJogoDesativado(){
        Long idExistente = 1L;
        var jogo = criarJogoAuxiliar();

        Mockito.when(jogoRepository.findById(idExistente)).thenReturn(Optional.of(jogo));
        jogo.deletar();

        var exception = Assertions.assertThrows(RegrasDeNegocioException.class, () -> jogoService.desativar(idExistente));
            Assertions.assertEquals("Este Jogo já está desativado.",exception.getMessage());
    }

    @Test
    @DisplayName("Reativar deve tornar ativo em true")
    void garantirReativarEntidadeCorretamente(){
        Long idexistente = 1L;
        var jogo = criarJogoAuxiliar();

        jogo.deletar();
        Mockito.when(jogoRepository.findById(idexistente)).thenReturn(Optional.of(jogo));
        jogoService.reativar(idexistente);

        Assertions.assertTrue(jogo.getAtivo());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar ativar jogo já ativo")
    void deveLancarExcecaoJogoAtivo(){
        Long idExistente = 1L;
        var jogo = criarJogoAuxiliar();

        Mockito.when(jogoRepository.findById(idExistente)).thenReturn(Optional.of(jogo));


        var exception = Assertions.assertThrows(RegrasDeNegocioException.class,() -> jogoService.reativar(idExistente));
        Assertions.assertEquals("Este Jogo já está ativo.",exception.getMessage());
    }



    private  Jogo criarJogoAuxiliar(){
        var dadosJogo = new DadosCadastroJogo("TituloTest","0000","DescTest",CategoriaJogo.MMORPG);
        return new Jogo(dadosJogo);
    }


}