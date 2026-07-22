package br.com.thiago.cinegamingapi.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizaEmailUsuario(
        @NotBlank(message = "Email é obrigatório.")
        @Email
        String  email) {
}
