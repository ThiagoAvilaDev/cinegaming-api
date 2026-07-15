package br.com.thiago.cinegamingapi.infra.seguranca.autenticacao;

import br.com.thiago.cinegamingapi.domain.usuario.Usuario;
import br.com.thiago.cinegamingapi.infra.exceptions.RegrasDeNegocioException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {


    @Value("${api.security.token.secret}")
    private String secretKey;
    @Value("${api.security.token.issue}")
    private  String issue;

    public String  gerarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(issue)
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(expiracao(30))
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            throw new RegrasDeNegocioException("Erro ao gerar token JWT de acesso!");
        }
    }



    public String gerarRefreshToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(issue)
                    .withSubject(usuario.getId().toString())
                    .withExpiresAt(expiracao(120))
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            throw new RegrasDeNegocioException("Erro ao gerar RefreshToken!");
        }
    }

    public String verificarToken(String token){
        DecodedJWT decodedJWT;

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issue)
                    .build();

            decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();

        } catch (JWTVerificationException exception){
            throw new RegrasDeNegocioException("Erro ao verificar token JWT de acesso!");

        }
    }

    private Instant expiracao(Integer minutos) {
        return LocalDateTime.now().plusMinutes(minutos).toInstant(ZoneOffset.of("-03:00"));
    }

}
