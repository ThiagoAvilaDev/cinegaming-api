package br.com.thiago.cinegamingapi.domain.usuario.usecase;

import br.com.thiago.cinegamingapi.domain.usuario.Usuario;
import br.com.thiago.cinegamingapi.domain.usuario.UsuarioRepository;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosAtualizaEmailUsuario;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosListagemUsuario;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AtualizarEmailUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public AtualizarEmailUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public DadosListagemUsuario atualizaEmailPorId(DadosAtualizaEmailUsuario dados, Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        );
        usuario.atualizaEmail(dados);
        return new DadosListagemUsuario(usuario);
    }
}
