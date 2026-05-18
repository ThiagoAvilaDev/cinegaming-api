package br.com.thiago.cinegamingapi.infra.exceptions;

import org.springframework.validation.FieldError;

public record DadoErroValidacao(String campo, String mensagem) {

    public DadoErroValidacao(FieldError error){
        this(error.getField(),error.getDefaultMessage());
    }
}
