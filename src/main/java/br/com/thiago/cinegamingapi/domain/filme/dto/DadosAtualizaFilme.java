package br.com.thiago.cinegamingapi.domain.filme.dto;

import br.com.thiago.cinegamingapi.domain.filme.CategoriaFilme;

public record DadosAtualizaFilme(String titulo,
                                 String descricao,
                                 CategoriaFilme categoria ) {
}
