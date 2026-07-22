package br.com.thiago.cinegamingapi.domain.usuario.usecase;

import br.com.thiago.cinegamingapi.domain.usuario.Usuario;
import br.com.thiago.cinegamingapi.domain.usuario.UsuarioRepository;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosAtualizaNomeUsuario;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosListagemUsuario;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AtualizarNomeUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public AtualizarNomeUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public DadosListagemUsuario atualizaNomePorId(DadosAtualizaNomeUsuario dados, Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        );
        usuario.atualizaNome(dados);
        return new DadosListagemUsuario(usuario);
    }

}
