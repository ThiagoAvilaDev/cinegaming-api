package br.com.thiago.cinegamingapi.infra.seguranca.autenticacao.dto;

public record DadosToken(
        String token,
        String refreshToken
) {
}
