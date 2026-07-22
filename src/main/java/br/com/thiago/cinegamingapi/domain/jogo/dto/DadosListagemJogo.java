package br.com.thiago.cinegamingapi.domain.jogo.dto;

import br.com.thiago.cinegamingapi.domain.jogo.CategoriaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.Jogo;

public record DadosListagemJogo(Long id,
                                String titulo,
                                String anoLancamento,
                                String descricao,
                                CategoriaJogo categoriaJogo) {

    public DadosListagemJogo(Jogo dados){
        this(
                dados.getId(),
                dados.getTitulo(),
                dados.getAnoLancamento(),
                dados.getDescricao(),
                dados.getCategoriaJogo()
        );
    }


}
