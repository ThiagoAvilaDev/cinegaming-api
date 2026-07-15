package br.com.thiago.cinegamingapi.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank(message = "Nome é obrigatório.")
        String nomeCompleto,
        @NotBlank(message = "Email é obrigatório.")
        @Email
        String email,
        @NotBlank(message = "Senha é obrigatória.")
        @Size(min = 8, message = "O tamanho da senha deve ser igual ou maior que 8")
        String senha) {
}
