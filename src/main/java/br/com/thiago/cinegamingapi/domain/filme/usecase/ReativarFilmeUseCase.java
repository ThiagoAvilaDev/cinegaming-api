package br.com.thiago.cinegamingapi.domain.filme.usecase;

import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReativarFilmeUseCase {
    private final FilmeRepository filmeRepository;

    public ReativarFilmeUseCase(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }


    @Transactional
    public DadosListagemFilme reativar(Long id) {
        var filme = filmeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Id informado não existe.")
        );
        if (filme.getAtivo()){
            throw new RegrasDeNegocioException("Este filme já está ativo.");
        }
        filme.reativar();

        return new DadosListagemFilme(filme);
    }
}
