package br.com.thiago.cinegamingapi.infra.seguranca.autenticacao;

import br.com.thiago.cinegamingapi.domain.usuario.Usuario;
import br.com.thiago.cinegamingapi.domain.usuario.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class AutenticacaoService {

    private  final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    public DadosToken autenticarLoginUsuario(DadosLogin dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(),dados.senha());
        var autenticarUsuario = authenticationManager
                .authenticate(authenticationToken);

        Usuario usuario = (Usuario) ofNullable(autenticarUsuario.getPrincipal()).orElseThrow();


        var tokenAcesso = tokenService.gerarToken(usuario);
        var refreshToken = tokenService.gerarRefreshToken(usuario);

        return new DadosToken(tokenAcesso,refreshToken);
    }

    public DadosToken  atualizarRefreshToken(String refreshToken) {
        var token = tokenService.verificarToken(refreshToken);
        Long idUsuario = Long.valueOf(token);

        var usuario = usuarioRepository.findById(idUsuario).orElseThrow();

        var tokenAcesso = tokenService.gerarToken(usuario);
        var tokenAtualizado = tokenService.gerarRefreshToken(usuario);

        return new DadosToken(tokenAcesso,tokenAtualizado);

    }
}
