package br.com.thiago.cinegamingapi.infra.seguranca.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record DadosRefreshToken(
        @NotBlank(message = "Campo não pode estar vazio")
        String refreshToken
) {
}
