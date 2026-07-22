package br.com.thiago.cinegamingapi.domain.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosAtualizaSenhaUsuario(
        @NotBlank(message = "Senha incorreta por favor verifique  a senha novamente.")
        @Size(min = 8) String  senhaAtual,
        @NotBlank @Size(min = 8, message = "Tamanho da senha não deve ser menor que 8 caracteres.")
        String novaSenha
) {
}
