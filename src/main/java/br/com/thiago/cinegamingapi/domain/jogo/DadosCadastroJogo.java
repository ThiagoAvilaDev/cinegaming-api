package br.com.thiago.cinegamingapi.domain.jogo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroJogo(
        @NotBlank(message = "Titulo não Pode ficar vazio.")
        String  titulo,
        @NotBlank(message = "Ano de lançamento não pode ser vazio.")
        String anoLancamento,
        @NotNull
        String descricao,
        @NotNull
        CategoriaJogo categoriaJogo) {
}
