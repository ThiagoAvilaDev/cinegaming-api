package br.com.thiago.cinegamingapi.domain.usuario.usecase;

import br.com.thiago.cinegamingapi.domain.usuario.Usuario;
import br.com.thiago.cinegamingapi.domain.usuario.UsuarioRepository;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosAtualizaSenhaUsuario;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosListagemUsuario;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AtualizarSenhaUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AtualizarSenhaUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public DadosListagemUsuario atualizaSenhaPorId(DadosAtualizaSenhaUsuario dados, Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        );
        if (!passwordEncoder.matches(dados.senhaAtual(), usuario.getPassword())){
            throw new RegrasDeNegocioException("A senha informada está incorreta");
        }

        var encode = passwordEncoder.encode(dados.novaSenha());
        usuario.atualizaSenha(encode);
        return new DadosListagemUsuario(usuario);
    }
}
