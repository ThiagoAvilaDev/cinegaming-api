package br.com.thiago.cinegamingapi.domain.filme;

import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
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
class FilmeServiceTest {

    @Mock
    FilmeRepository repository;
    @InjectMocks
    FilmeService filmeService;

    @Test
    @DisplayName("Deve impedir a atualização do filme se estiver com ativo false")
    void cenario1(){
        //Arrange
        Long idExistente = 1L;
        var filme = criarFilmeAuxiliar();
        var dados = new DadosAtualizaFilme("TituloTest","DescTest", CategoriaFilme.ACAO);

        filme.desativar();
        //Act
        Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(filme));

        //Assert
        var exception = Assertions.assertThrows(RegrasDeNegocioException.class,() -> filmeService.atualizaDados(idExistente,dados));
        Assertions.assertEquals("A entidade informada não pode ser modificada.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve impedir a atualização iD inexistente")
    void cenario2(){
        //Arrange
        Long idInexistente = 2L;
        var dados = new DadosAtualizaFilme("TituloTest","DescTest", CategoriaFilme.ACAO);

        //Act + Assert
        var exception = Assertions.assertThrows(EntityNotFoundException.class,() -> filmeService.atualizaDados(idInexistente,dados));
        Assertions.assertEquals("Id informado não existe.", exception.getMessage());
    }


    @Test
    @DisplayName("Exclusão lógica deve tornar um ativo true em false")
    void cenario3(){
        //Arrange
        Long idExistente = 1L;
        var filme = criarFilmeAuxiliar();

        //ACT
        Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(filme));
        filmeService.excluir(idExistente);

        //Assertion
        Assertions.assertFalse(filme.getAtivo());
    }


    private Filme criarFilmeAuxiliar(){
        var dadosCadastro = new DadosCadastroFilme("Filme Test","Desc Test", CategoriaFilme.ACAO);
        return new Filme(dadosCadastro);
    }


}