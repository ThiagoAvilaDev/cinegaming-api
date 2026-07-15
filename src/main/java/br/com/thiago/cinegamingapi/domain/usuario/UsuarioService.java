package br.com.thiago.cinegamingapi.domain.usuario;

import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public DadosListagemUsuario cadastrar(DadosCadastroUsuario dados) {
        String encode = passwordEncoder.encode(dados.senha());
        Usuario usuario = usuarioRepository.save(new Usuario(dados,encode));

        return new DadosListagemUsuario(usuario);
    }

    public Page<DadosListagemUsuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAllByAtivoTrue(pageable).map(DadosListagemUsuario::new);
    }

    public DadosListagemUsuario buscarPorId(Long id) {
        return new DadosListagemUsuario( usuarioRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        ));
    }

    @Transactional
    public DadosListagemUsuario atualizaNomePorId(DadosAtualizaNomeUsuario dados, Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        );
        usuario.atualizaNome(dados);
        return new DadosListagemUsuario(usuario);
    }

    @Transactional
    public DadosListagemUsuario atualizaEmailPorId(DadosAtualizaEmailUsuario dados, Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        );
        usuario.atualizaEmail(dados);
        return new DadosListagemUsuario(usuario);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCaseAndAtivoTrue(username).orElseThrow(
                () -> new UsernameNotFoundException("O usuário não foi encontrado!"));
    }
}
