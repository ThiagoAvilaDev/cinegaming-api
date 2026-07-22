package br.com.thiago.cinegamingapi.domain.jogo.controller;

import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosAtualizaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosCadastroJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import br.com.thiago.cinegamingapi.domain.jogo.usecase.*;
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

@WebMvcTest(JogoController.class)
@AutoConfigureJsonTesters
@Import(SecurityConfig.class)
class JogoControllerTest {

    @Autowired
    private MockMvcTester mvc;
    @Autowired
    private JacksonTester<DadosCadastroJogo> jsonCadastro;
    @Autowired
    private JacksonTester<DadosAtualizaJogo> jsonAtualiza;

    @MockitoBean
    private CadastrarJogoUseCase cadastrarJogoUseCase;
    @MockitoBean
    private ConsultarJogoUseCase consultarJogoUseCase;
    @MockitoBean
    private AtualizarJogoUseCase atualizarJogoUseCase;
    @MockitoBean
    private DesativarJogoUseCase desativarJogoUseCase;
    @MockitoBean
    private ReativarJogoUseCase reativarJogoUseCase;

    @MockitoBean
    private Usuario usuario;
    @MockitoBean
    private UsuarioRepository usuarioRepository;
    @MockitoBean
    private TokenService tokenService;





    @Test
    @DisplayName("Deve garantir o cadastro correto das entidades")
    void garantirCadastroEntidades() throws IOException {
        var dadosCadastro = new DadosCadastroJogo("TituloTest","2000","DescTest", CategoriaJogo.MMORPG);
        var dadosDetalhamento = new DadosListagemJogo(1L,"TituloTest","2000","DescTest", CategoriaJogo.MMORPG);

        var tokenFalso = autenticacaoSimulada();

        Mockito.when(cadastrarJogoUseCase.cadastrar(dadosCadastro)).thenReturn(dadosDetalhamento);

        assertThat(this.mvc.post()
                .uri("/jogo")
                .header("Authorization", "Bearer " + tokenFalso)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCadastro.write(dadosCadastro).getJson()))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .convertTo(DadosListagemJogo.class)
                .isEqualTo(dadosDetalhamento);
    }

    @Test
    @DisplayName("Deve garantir a atualização correta das entidades retorne um DTO")
    void  garantirAtualizacaoCorreta() throws IOException {
        Long idExistente = 1L;
        var atualiza = new DadosAtualizaJogo("TituloAtualizado","2000","DescAtualizacao",CategoriaJogo.MMORPG);
        var dadosDetalhamento = new DadosListagemJogo(idExistente,"TituloAtualizado","2000","DescAtualizacao",CategoriaJogo.MMORPG);

        var tokenFalso = autenticacaoSimulada();
        Mockito.when(atualizarJogoUseCase.atualizaDados(idExistente,atualiza)).thenReturn(dadosDetalhamento);

        assertThat(this.mvc.patch()
                .uri("/jogo/{id}",idExistente)
                .header("Authorization", "Bearer " + tokenFalso)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAtualiza.write(atualiza).getJson()))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemJogo.class)
                .isEqualTo(dadosDetalhamento);


    }

    @Test
    @DisplayName("Deve garantir o retorno correto  ao deletar a entidade")
    void garantirExclusaoCorreta(){
        Long idExistente = 1L;
        var tokenFalso = autenticacaoSimulada();
        assertThat(mvc.delete()
                .uri("/jogo/{id}",idExistente)
                .header("Authorization", "Bearer " + tokenFalso))
                .hasStatus(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("Deve garantir o retorno correto  ao Reativar a entidade")
    void garantirReativacaoCorreta(){
        Long idExistente = 1L;
        var dadosDetalhamento = new DadosListagemJogo(idExistente,"TituloAtualizado","2000","DescAtualizacao",CategoriaJogo.MMORPG);
        var tokenFalso = autenticacaoSimulada();
        Mockito.when(reativarJogoUseCase.reativar(idExistente)).thenReturn(dadosDetalhamento);

        assertThat(mvc.patch()
                .uri("/jogo/reativar/{id}",idExistente)
                .header("Authorization", "Bearer " + tokenFalso))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(DadosListagemJogo.class)
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