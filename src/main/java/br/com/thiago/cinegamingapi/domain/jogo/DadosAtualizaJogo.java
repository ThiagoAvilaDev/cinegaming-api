package br.com.thiago.cinegamingapi.domain.jogo;

public record DadosAtualizaJogo(
        String titulo,
        String anoLancamento,
        String descricao,
        CategoriaJogo categoriaJogo) {
}
