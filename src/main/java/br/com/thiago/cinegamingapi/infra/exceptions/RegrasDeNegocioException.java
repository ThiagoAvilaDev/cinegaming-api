package br.com.thiago.cinegamingapi.infra.exceptions;

public class RegrasDeNegocioException extends RuntimeException {
    public RegrasDeNegocioException(String message) {
        super(message);
    }
}
