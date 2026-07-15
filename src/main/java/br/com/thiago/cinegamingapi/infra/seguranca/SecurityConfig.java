package br.com.thiago.cinegamingapi.infra.seguranca;

import br.com.thiago.cinegamingapi.infra.seguranca.filtros.FiltroToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final FiltroToken filtroToken;

    public SecurityConfig(FiltroToken filtroToken) {
        this.filtroToken = filtroToken;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http

                .authorizeHttpRequests(req ->
                        {
                            req.requestMatchers(HttpMethod.POST,"/usuario","/login","/atualizar-token").permitAll();
                            req.anyRequest().authenticated();
                        }
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(filtroToken, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();

    }



}
