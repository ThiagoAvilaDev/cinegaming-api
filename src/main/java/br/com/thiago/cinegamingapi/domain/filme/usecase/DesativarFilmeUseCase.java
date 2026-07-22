package br.com.thiago.cinegamingapi.domain.filme.usecase;

import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DesativarFilmeUseCase {
    private final FilmeRepository filmeRepository;

    public DesativarFilmeUseCase(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Transactional
    public void excluir(Long id) {
        var filme = filmeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Id informado não existe.")
        );
        if(!filme.getAtivo()){
            throw new RegrasDeNegocioException("Este Filme já está desativado.");
        }
        filme.desativar();

    }
}
