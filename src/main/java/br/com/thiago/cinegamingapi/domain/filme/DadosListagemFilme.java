package br.com.thiago.cinegamingapi.domain.filme;

public record DadosListagemFilme(Long id,
                                 String titulo,
                                 CategoriaFilme categoria,
                                 String descricao)
{

    public DadosListagemFilme(Filme filme){
        this(filme.getId(),filme.getTitulo(),filme.getCategoria(),filme.getDescricao());
    }
}
