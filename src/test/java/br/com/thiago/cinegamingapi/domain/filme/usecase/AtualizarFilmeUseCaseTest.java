package br.com.thiago.cinegamingapi.domain.filme.usecase;

import br.com.thiago.cinegamingapi.domain.filme.CategoriaFilme;
import br.com.thiago.cinegamingapi.domain.filme.Filme;
import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosAtualizaFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosCadastroFilme;
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
class AtualizarFilmeUseCaseTest {

    @Mock
    FilmeRepository repository;
    @InjectMocks
    AtualizarFilmeUseCase atualizarFilmeUseCase;
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
        var exception = Assertions.assertThrows(RegrasDeNegocioException.class,() -> atualizarFilmeUseCase.atualizaDados(idExistente,dados));
        Assertions.assertEquals("A entidade informada não pode ser modificada.", exception.getMessage());
    }


    @Test
    @DisplayName("Deve impedir a atualização iD inexistente")
    void cenario2(){
        //Arrange
        Long idInexistente = 2L;
        var dados = new DadosAtualizaFilme("TituloTest","DescTest", CategoriaFilme.ACAO);

        //Act + Assert
        var exception = Assertions.assertThrows(EntityNotFoundException.class,() -> atualizarFilmeUseCase.atualizaDados(idInexistente,dados));
        Assertions.assertEquals("Id informado não existe.", exception.getMessage());
    }

    private Filme criarFilmeAuxiliar(){
        var dadosCadastro = new DadosCadastroFilme("Filme Test","Desc Test", CategoriaFilme.ACAO);
        return new Filme(dadosCadastro);
    }

}