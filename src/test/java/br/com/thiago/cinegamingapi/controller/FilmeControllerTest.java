package br.com.thiago.cinegamingapi.controller;

import br.com.thiago.cinegamingapi.domain.filme.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(FilmeController.class)
@AutoConfigureJsonTesters
class FilmeControllerTest {

    @Autowired
    private MockMvcTester mvcTester;
    @Autowired
    private JacksonTester<DadosCadastroFilme> jsonCadastro;
    @Autowired
    private JacksonTester<DadosAtualizaFilme> jsonAtualiza;

    @MockitoBean
    private FilmeService filmeService;



    @Test
    @DisplayName("Garantir o Retorno e cadastro correto da entidade Filme")
    void garantirCadastroEntidadeFilme() throws IOException {
        var dadosCadastro = new DadosCadastroFilme("TituloCadastro","DescricaoCadastro", CategoriaFilme.ACAO);
        var dadosDetalhamento = new DadosListagemFilme(1L,"TituloCadastro", CategoriaFilme.ACAO,"DescricaoCadastro");


        Mockito.when(filmeService.adicionarFilme(dadosCadastro)).thenReturn(dadosDetalhamento);

        assertThat(this.mvcTester.post()
                .uri("/filme")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCadastro.write(dadosCadastro).getJson()))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .convertTo(DadosListagemFilme.class)
                .isEqualTo(dadosDetalhamento);

    }


    @Test
    @DisplayName("Deve garantir a atualização correta da entidade Filme")
    void garantirAtualizacaoCorretaEntidadeFilme() throws IOException {
        Long idExistente = 1L;
        var dadosAtualiza = new DadosAtualizaFilme("TituloAtualiza","DescricaoAtualiza", CategoriaFilme.ACAO);
        var dadosDetalhamento = new DadosListagemFilme(idExistente,"TituloAtualiza", CategoriaFilme.ACAO,"DescricaoAtualiza");


        Mockito.when(filmeService.atualizaDados(idExistente,dadosAtualiza)).thenReturn(dadosDetalhamento);

        assertThat(this.mvcTester.patch().uri("/filme/{id}",idExistente)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAtualiza.write(dadosAtualiza).getJson()))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemFilme.class)
                .isEqualTo(dadosDetalhamento);



    }

    @Test
    @DisplayName("Deve Garantir o retorno correto da exclusão lógica")
    void garantirExclusaoCorreta(){
        Long idExistente = 1L;

        assertThat(this.mvcTester.delete()
                .uri("/filme/{id}",idExistente))
                .hasStatus(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Deve garantir o retorno correto ao reativar a entidade Filme")
    void garantirReativacaoCorreta() {
        Long idExistente = 1L;
        var dadosDetalhamento = new DadosListagemFilme(idExistente,"TituloTest", CategoriaFilme.ACAO,"DescricaoTest");

        Mockito.when(filmeService.reativar(idExistente)).thenReturn(dadosDetalhamento);


        assertThat(this.mvcTester.patch()
                .uri("/filme/reativar/{id}",idExistente))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemFilme.class)
                .isEqualTo(dadosDetalhamento);
    }

}