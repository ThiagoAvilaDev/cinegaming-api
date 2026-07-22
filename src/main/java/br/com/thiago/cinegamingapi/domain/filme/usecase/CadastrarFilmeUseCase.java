package br.com.thiago.cinegamingapi.domain.filme.usecase;

import br.com.thiago.cinegamingapi.domain.filme.dto.DadosCadastroFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
import br.com.thiago.cinegamingapi.domain.filme.Filme;
import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CadastrarFilmeUseCase {

    private final FilmeRepository filmeRepository;

    public CadastrarFilmeUseCase(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Transactional
    public DadosListagemFilme cadastraFilme(DadosCadastroFilme dadosFilme) {
        var filme = new Filme(dadosFilme);
        filmeRepository.save(filme);
        return new DadosListagemFilme(filme);
    }
}
