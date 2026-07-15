package br.com.thiago.cinegamingapi.infra.seguranca.autenticacao;

public record DadosToken(
        String token,
        String refreshToken
) {
}
