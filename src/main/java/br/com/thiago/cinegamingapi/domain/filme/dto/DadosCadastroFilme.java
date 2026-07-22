package br.com.thiago.cinegamingapi.domain.filme.dto;

import br.com.thiago.cinegamingapi.domain.filme.CategoriaFilme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroFilme(
        @NotBlank(message = "O campo Título não pode estar vazio.") String titulo,
        @NotBlank(message = "Insira uma descrição") String descricao,
        @NotNull CategoriaFilme categoria) {
}
