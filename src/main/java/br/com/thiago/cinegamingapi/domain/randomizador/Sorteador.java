package br.com.thiago.cinegamingapi.domain.randomizador;

import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;

import java.util.concurrent.ThreadLocalRandom;

public final class Sorteador {

    private Sorteador() {
        throw new RegrasDeNegocioException("Esta Classe por Regra não pode Ser instanciada!");
    }
    public static int sorteador(int tamanhoLista){

        return ThreadLocalRandom.current().nextInt(tamanhoLista);
    }
}
