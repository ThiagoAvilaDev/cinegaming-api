package br.com.thiago.cinegamingapi.domain.jogo;

import jakarta.persistence.*;

@Entity(name = "jogo")
@Table(name = "jogos")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  titulo;
    private String anoLancamento;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private CategoriaJogo categoriaJogo;
    private Boolean ativo;

    public Jogo(){}

    public Jogo(DadosCadastroJogo dados){
        this.titulo = dados.titulo();
        this.anoLancamento = dados.anoLancamento();
        this.descricao = dados.descricao();
        this.categoriaJogo = dados.categoriaJogo();
        this.ativo = true;

    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaJogo getCategoriaJogo() {
        return categoriaJogo;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void atualizaDados(DadosAtualizaJogo dados) {
        if (dados.titulo() != null && !dados.titulo().isBlank()){
            this.titulo = dados.titulo().trim();
        }
        if (dados.anoLancamento() != null && !dados.anoLancamento().isBlank()){
            this.anoLancamento = dados.anoLancamento().trim();
        }
        if (dados.descricao() != null && !dados.descricao().isBlank()) {
            this.descricao = dados.descricao().trim();
        }
        if (dados.categoriaJogo() != null){
            this.categoriaJogo = dados.categoriaJogo();
        }
    }

    public void deletar() {
        this.ativo = false;
    }


    public void reativar() {
        this.ativo = true;
    }
}
