package br.com.thiago.cinegamingapi.domain.filme;

import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }


    @Transactional
    public DadosListagemFilme adicionarFilme(DadosCadastroFilme dados) {
        var filme = new Filme(dados);
        filmeRepository.save(filme);
        return new DadosListagemFilme(filme);
    }

    public Page<DadosListagemFilme> listarFilmes(Pageable pageable) {
        return filmeRepository.findAllByAtivoTrue(pageable).map(DadosListagemFilme::new);
    }

    public Page<DadosListagemFilme> listarFilmesPorCategoria(Categoria categoria,Pageable pageable) {
        return filmeRepository.findByCategoria(categoria,pageable).map(DadosListagemFilme::new);

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

    public Page<DadosListagemFilme> buscaPorTitulo(String titulo,Pageable pageable) {
        var filme = filmeRepository.findByTituloContainingIgnoreCaseAndAtivoTrue(titulo,pageable);
        return filme.map(DadosListagemFilme::new);
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
