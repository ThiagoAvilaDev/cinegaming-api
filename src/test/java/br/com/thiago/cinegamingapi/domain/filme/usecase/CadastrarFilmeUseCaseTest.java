package br.com.thiago.cinegamingapi.domain.filme.usecase;

import br.com.thiago.cinegamingapi.domain.filme.CategoriaFilme;
import br.com.thiago.cinegamingapi.domain.filme.Filme;
import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosCadastroFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CadastrarFilmeUseCaseTest {

    @Mock
    private FilmeRepository filmeRepository;
    @InjectMocks
    private CadastrarFilmeUseCase cadastrarFilmeUseCase;


    @Test
    @DisplayName("Dado filme com todos os campos quando salvo no repository então deveria retornar DTO")
    void cenarioSucesso(){
        //Dado
        var dto = new DadosCadastroFilme("Titulo Filme","Descricao Filme", CategoriaFilme.ACAO);
        var filmeNovo = new Filme(dto);

        Mockito.when(filmeRepository.save(Mockito.any(Filme.class))).thenReturn(filmeNovo);

        //Quando + Então
        assertEquals(cadastrarFilmeUseCase.cadastraFilme(dto),new DadosListagemFilme(filmeNovo));


    }






}