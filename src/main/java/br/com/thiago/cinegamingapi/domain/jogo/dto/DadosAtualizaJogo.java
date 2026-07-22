package br.com.thiago.cinegamingapi.domain.jogo.dto;

import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;

public record DadosAtualizaJogo(
        String titulo,
        String anoLancamento,
        String descricao,
        CategoriaJogo categoriaJogo) {
}
