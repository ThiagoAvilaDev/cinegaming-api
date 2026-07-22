package br.com.thiago.cinegamingapi.domain.usuario.usecase;

import br.com.thiago.cinegamingapi.domain.usuario.UsuarioRepository;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosListagemUsuario;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public ConsultarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Page<DadosListagemUsuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAllByAtivoTrue(pageable).map(DadosListagemUsuario::new);
    }

    public DadosListagemUsuario buscarPorId(Long id) {
        return new DadosListagemUsuario( usuarioRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        ));
    }
}
