package br.com.thiago.cinegamingapi.domain.filme;

import jakarta.persistence.*;

@Entity(name = "filme")
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private String descricao;
    private Boolean ativo;


    public Filme(){}

    public Filme(DadosCadastroFilme dados) {
      this.titulo = dados.titulo();
      this.categoria = dados.categoria();
      this.descricao = dados.descricao();
      this.ativo = true;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void atualizaDados(DadosAtualizaFilme dados) {
        if (dados.titulo() != null && !dados.titulo().isBlank()){
            this.titulo = dados.titulo();
        }
        if (dados.categoria() != null) {
            this.categoria = dados.categoria();
        }
        if (dados.descricao() != null  && !dados.descricao().isBlank()) {
            this.descricao = dados.descricao();
        }
    }

    public void desativar() {
        this.ativo = false;
    }

    public void reativar() {this.ativo = true;
    }
}
