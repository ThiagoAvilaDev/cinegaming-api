package br.com.thiago.cinegamingapi.domain.jogo;

import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JogoService {

    private final JogoRepository jogoRepository;

    public JogoService(JogoRepository jogoRepository) {
        this.jogoRepository = jogoRepository;
    }

    @Transactional
    public DadosListagemJogo cadastrar(DadosCadastroJogo dados){
     var jogo = new Jogo(dados);
     jogoRepository.save(jogo);

     return new DadosListagemJogo(jogo);
    }



    public Page<DadosListagemJogo> listar(Pageable page) {
        return jogoRepository.findAllByAtivoTrue(page).map(DadosListagemJogo::new);
    }

    public Page<DadosListagemJogo> listarPorCategoria(CategoriaJogo categoria, Pageable page) {
        return jogoRepository.findAllByCategoriaJogoAndAtivoTrue(categoria,page).map(DadosListagemJogo::new);
    }

    public Page<DadosListagemJogo> listarPorTitulo(String titulo, Pageable page) {
        return jogoRepository.findAllByTituloContainingIgnoreCaseAndAtivoTrue(titulo, page).map(DadosListagemJogo::new);
    }

    @Transactional
    public DadosListagemJogo atualizaDados(Long id,DadosAtualizaJogo dados) {
        var jogo = jogoRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        );
        if (!jogo.getAtivo()){
            throw new RegrasDeNegocioException("A entidade informada não pode ser modificada.");
        }
        jogo.atualizaDados(dados);

        return new DadosListagemJogo(jogo);
    }

    @Transactional
    public void desativar(Long id) {
       var jogo =  jogoRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        );
       jogo.deletar();
    }

    @Transactional
    public DadosListagemJogo reativar(Long id) {
        var jogo =  jogoRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        );
        if (jogo.getAtivo()){
            throw new RegrasDeNegocioException("Este Jogo já está ativo.");
        }
        jogo.reativar();
        return new DadosListagemJogo(jogo);
    }
}
