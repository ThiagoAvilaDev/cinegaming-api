package br.com.thiago.cinegamingapi.domain.jogo.usecase;

import br.com.thiago.cinegamingapi.domain.jogo.JogoRepository;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReativarJogoUseCase {

    private final JogoRepository jogoRepository;

    public ReativarJogoUseCase(JogoRepository jogoRepository) {
        this.jogoRepository = jogoRepository;
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
