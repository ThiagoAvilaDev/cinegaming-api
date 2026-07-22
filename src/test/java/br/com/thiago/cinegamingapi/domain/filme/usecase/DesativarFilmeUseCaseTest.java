package br.com.thiago.cinegamingapi.domain.filme.usecase;

import br.com.thiago.cinegamingapi.domain.filme.CategoriaFilme;
import br.com.thiago.cinegamingapi.domain.filme.Filme;
import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosCadastroFilme;
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
class DesativarFilmeUseCaseTest {

    @Mock
    private FilmeRepository repository;
    @InjectMocks
    private DesativarFilmeUseCase desativarFilmeUseCase;




    @Test
    @DisplayName("Exclusão lógica deve tornar um ativo true em false")
    void cenario3(){
        //Arrange
        Long idExistente = 1L;
        var filme = criarFilmeAuxiliar();

        //ACT
        Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(filme));
        desativarFilmeUseCase.excluir(idExistente);

        //Assertion
        Assertions.assertFalse(filme.getAtivo());
    }


    private Filme criarFilmeAuxiliar(){
        var dadosCadastro = new DadosCadastroFilme("Filme Test","Desc Test", CategoriaFilme.ACAO);
        return new Filme(dadosCadastro);
    }

}