package br.com.thiago.cinegamingapi.domain.filme.controller;

import br.com.thiago.cinegamingapi.domain.filme.*;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosAtualizaFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosCadastroFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
import br.com.thiago.cinegamingapi.domain.filme.usecase.*;
import br.com.thiago.cinegamingapi.domain.usuario.Usuario;
import br.com.thiago.cinegamingapi.domain.usuario.UsuarioRepository;
import br.com.thiago.cinegamingapi.infra.seguranca.SecurityConfig;
import br.com.thiago.cinegamingapi.infra.seguranca.autenticacao.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(FilmeController.class)
@AutoConfigureJsonTesters
@Import(SecurityConfig.class)
class FilmeControllerTest {

    @Autowired
    private MockMvcTester mvcTester;
    @Autowired
    private JacksonTester<DadosCadastroFilme> jsonCadastro;
    @Autowired
    private JacksonTester<DadosAtualizaFilme> jsonAtualiza;

    @MockitoBean
    private CadastrarFilmeUseCase cadastrarFilmeUseCase;
    @MockitoBean
    private AtualizarFilmeUseCase atualizarFilmeUseCase;
    @MockitoBean
    private ReativarFilmeUseCase reativarFilmeUseCase;
    @MockitoBean
    private ConsultarFilmeUseCase consultarFilmeUseCase;
    @MockitoBean
    private DesativarFilmeUseCase desativarFilmeUseCase;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private Usuario usuario;



    @Test
    @DisplayName("Garantir o cadastro e retorno correto da entidade Filme")
    void garantirCadastroEntidadeFilme() throws IOException {
        var dadosCadastro = new DadosCadastroFilme("TituloCadastro","DescricaoCadastro", CategoriaFilme.ACAO);
        var dadosDetalhamento = new DadosListagemFilme(1L,"TituloCadastro", CategoriaFilme.ACAO,"DescricaoCadastro");

        var tokenFalso = autenticacaoSimulada();

        Mockito.when(cadastrarFilmeUseCase.cadastraFilme(dadosCadastro)).thenReturn(dadosDetalhamento);

        assertThat(this.mvcTester.post()
                .uri("/filme")
                .header("Authorization","Bearer " +tokenFalso)
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

        var tokenFalso = autenticacaoSimulada();

        Mockito.when(atualizarFilmeUseCase.atualizaDados(idExistente,dadosAtualiza)).thenReturn(dadosDetalhamento);

        assertThat(this.mvcTester.patch()
                .uri("/filme/{id}",idExistente)
                .header("Authorization","Bearer " + tokenFalso)
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

        var tokenFalso = autenticacaoSimulada();

        assertThat(this.mvcTester.delete()
                .uri("/filme/{id}",idExistente)
                .header("Authorization","Bearer " + tokenFalso))
                .hasStatus(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Deve garantir o retorno correto ao reativar a entidade Filme")
    void garantirReativacaoCorreta() {
        Long idExistente = 1L;
        var dadosDetalhamento = new DadosListagemFilme(idExistente,"TituloTest", CategoriaFilme.ACAO,"DescricaoTest");

        var tokenFalso = autenticacaoSimulada();

        Mockito.when(reativarFilmeUseCase.reativar(idExistente)).thenReturn(dadosDetalhamento);


        assertThat(this.mvcTester.patch()
                .uri("/filme/reativar/{id}",idExistente)
                .header("Authorization","Bearer " + tokenFalso))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemFilme.class)
                .isEqualTo(dadosDetalhamento);
    }


    private String  autenticacaoSimulada(){
        var tokenFalso = "TokenFalso";
        var  email = "emailsimulado@email.com";
        Mockito.when(tokenService.verificarToken(tokenFalso)).thenReturn(email);
        Mockito.when(usuarioRepository.findByEmailIgnoreCaseAndAtivoTrue(email)).thenReturn(Optional.of(usuario));
        return tokenFalso;
    }

}