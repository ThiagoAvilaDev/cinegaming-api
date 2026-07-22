package br.com.thiago.cinegamingapi.domain.filme.usecase;

import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosAtualizaFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AtualizarFilmeUseCase {

    private final FilmeRepository filmeRepository;

    public AtualizarFilmeUseCase(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Transactional
    public DadosListagemFilme atualizaDados(Long id, DadosAtualizaFilme dados) {
        var filme = filmeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Id informado não existe.")
        );
        if (!filme.getAtivo()){
            throw new RegrasDeNegocioException("A entidade informada não pode ser modificada.");
        }
        filme.atualizaDados(dados);

        return new DadosListagemFilme(filme);
    }
}
