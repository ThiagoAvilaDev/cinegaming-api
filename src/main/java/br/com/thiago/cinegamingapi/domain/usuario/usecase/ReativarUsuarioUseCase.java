package br.com.thiago.cinegamingapi.domain.usuario.usecase;

import br.com.thiago.cinegamingapi.domain.usuario.Usuario;
import br.com.thiago.cinegamingapi.domain.usuario.UsuarioRepository;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosListagemUsuario;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReativarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public ReativarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public DadosListagemUsuario reativarUsuarioPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe"));

        if (usuario.getAtivo()){
            throw  new RegrasDeNegocioException("Usuário já ativado");
        }
        usuario.ativar();
        return new DadosListagemUsuario(usuario);

    }
}
