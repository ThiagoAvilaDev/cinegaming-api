package br.com.thiago.cinegamingapi.domain.jogo;

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
