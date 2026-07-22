package br.com.thiago.cinegamingapi.domain.usuario.usecase;

import br.com.thiago.cinegamingapi.domain.usuario.Usuario;
import br.com.thiago.cinegamingapi.domain.usuario.UsuarioRepository;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosCadastroUsuario;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosListagemUsuario;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CadastrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CadastrarUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public DadosListagemUsuario cadastrar(DadosCadastroUsuario dados) {
        String encode = passwordEncoder.encode(dados.senha());
        Usuario usuario = usuarioRepository.save(new Usuario(dados,encode));

        return new DadosListagemUsuario(usuario);
    }
}
