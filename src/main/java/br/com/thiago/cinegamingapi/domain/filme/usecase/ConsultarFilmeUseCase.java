package br.com.thiago.cinegamingapi.domain.filme.usecase;

import br.com.thiago.cinegamingapi.domain.filme.CategoriaFilme;
import br.com.thiago.cinegamingapi.domain.filme.dto.DadosListagemFilme;
import br.com.thiago.cinegamingapi.domain.filme.FilmeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultarFilmeUseCase {

    private final FilmeRepository filmeRepository;

    public ConsultarFilmeUseCase(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }


    public Page<DadosListagemFilme> listarFilmes(Pageable pageable) {
        return filmeRepository.findAllByAtivoTrue(pageable).map(DadosListagemFilme::new);
    }

    public Page<DadosListagemFilme> listarFilmesPorCategoria(CategoriaFilme categoria, Pageable pageable) {
        return filmeRepository.findAllByCategoriaAndAtivoTrue(categoria,pageable).map(DadosListagemFilme::new);

    }

    public Page<DadosListagemFilme> buscaPorTitulo(String titulo,Pageable pageable) {
        var filme = filmeRepository.findByTituloContainingIgnoreCaseAndAtivoTrue(titulo,pageable);
        return filme.map(DadosListagemFilme::new);
    }
}
