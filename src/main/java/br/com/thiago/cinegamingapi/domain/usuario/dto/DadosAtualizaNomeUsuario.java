package br.com.thiago.cinegamingapi.domain.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizaNomeUsuario(
        @NotBlank(message = "Nome é obrigatório.")
        String nomeCompleto) {
}
