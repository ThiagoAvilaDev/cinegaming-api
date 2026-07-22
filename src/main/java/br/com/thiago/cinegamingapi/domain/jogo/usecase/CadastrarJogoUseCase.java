package br.com.thiago.cinegamingapi.domain.jogo.usecase;

import br.com.thiago.cinegamingapi.domain.jogo.Jogo;
import br.com.thiago.cinegamingapi.domain.jogo.JogoRepository;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosCadastroJogo;
import br.com.thiago.cinegamingapi.domain.jogo.dto.DadosListagemJogo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CadastrarJogoUseCase {

    private final JogoRepository jogoRepository;

    public CadastrarJogoUseCase(JogoRepository jogoRepository) {
        this.jogoRepository = jogoRepository;
    }

    @Transactional
    public DadosListagemJogo cadastrar(DadosCadastroJogo dados){
        var jogo = new Jogo(dados);
        jogoRepository.save(jogo);

        return new DadosListagemJogo(jogo);
    }


}
