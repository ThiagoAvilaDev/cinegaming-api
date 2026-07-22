package br.com.thiago.cinegamingapi.domain.usuario.dto;

import br.com.thiago.cinegamingapi.domain.usuario.Usuario;

public record DadosListagemUsuario(Long id, String  nomeCompleto, String email) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNomeCompleto(), usuario.getUsername());
    }
}
