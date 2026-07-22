package br.com.thiago.cinegamingapi.domain.usuario;

import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosAtualizaEmailUsuario;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosAtualizaNomeUsuario;
import br.com.thiago.cinegamingapi.domain.usuario.dto.DadosCadastroUsuario;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "usuario")
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;
    private String email;
    private String senha;
    private Boolean ativo;



    public Usuario(){}

    public Usuario(DadosCadastroUsuario dados, String encode) {
        this.nomeCompleto = dados.nomeCompleto();
        this.email = dados.email();
        this.senha = encode;
        this.ativo = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public Boolean getAtivo() {
        return ativo;
    }


    public void atualizaNome(DadosAtualizaNomeUsuario dados) {
        this.nomeCompleto = dados.nomeCompleto().trim();

    }


    public void atualizaEmail(DadosAtualizaEmailUsuario dados) {
        this.email = dados.email().trim();
    }

    public void atualizaSenha(String senha) {
        this.senha = senha;
    }

    public void deletar() {
        this.ativo = false;
    }


    public void ativar() {
        this.ativo = true;
    }
}
