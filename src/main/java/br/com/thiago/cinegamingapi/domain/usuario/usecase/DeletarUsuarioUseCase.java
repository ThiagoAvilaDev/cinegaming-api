package br.com.thiago.cinegamingapi.domain.usuario.usecase;

import br.com.thiago.cinegamingapi.domain.usuario.Usuario;
import br.com.thiago.cinegamingapi.domain.usuario.UsuarioRepository;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeletarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public DeletarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void deletarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        );
        if (!usuario.getAtivo()){
            throw  new RegrasDeNegocioException("Usuário já deletado");
        }
        usuario.deletar();
    }
}
