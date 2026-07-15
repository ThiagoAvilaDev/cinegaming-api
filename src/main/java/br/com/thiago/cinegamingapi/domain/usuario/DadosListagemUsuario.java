package br.com.thiago.cinegamingapi.domain.usuario;

public record DadosListagemUsuario(Long id, String  nomeCompleto, String email) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNomeCompleto(), usuario.getUsername());
    }
}
