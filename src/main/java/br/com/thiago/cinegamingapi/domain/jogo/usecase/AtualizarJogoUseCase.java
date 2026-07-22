package br.com.thiago.cinegamingapi.domain.jogo.usecase;

import br.com.thiago.cinegamingapi.domain.jogo.JogoRepository;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosAtualizaJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AtualizarJogoUseCase {
    private final JogoRepository jogoRepository;

    public AtualizarJogoUseCase(JogoRepository jogoRepository) {
        this.jogoRepository = jogoRepository;
    }

    @Transactional
    public DadosListagemJogo atualizaDados(Long id, DadosAtualizaJogo dados) {
        var jogo = jogoRepository.findById(id).orElseThrow(
                () -> new RegrasDeNegocioException("O Id informado não existe")
        );
        if (!jogo.getAtivo()){
            throw new RegrasDeNegocioException("A entidade informada não pode ser modificada.");
        }
        jogo.atualizaDados(dados);

        return new DadosListagemJogo(jogo);
    }
}
