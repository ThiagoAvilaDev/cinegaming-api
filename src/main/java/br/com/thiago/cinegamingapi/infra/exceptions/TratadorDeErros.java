package br.com.thiago.cinegamingapi.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadoErroValidacao>> tratarErrorCamposInvalidos(MethodArgumentNotValidException exception){
        var erros = exception.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(DadoErroValidacao::new).toList());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DadoErrorMensagem> tratarErrorCampoEnum() {
        return ResponseEntity.badRequest().body(new DadoErrorMensagem("Existe um erro de formato em um dos campos, confira se está tudo certo."));
    }

    @ExceptionHandler(RegrasDeNegocioException.class)
    public ResponseEntity<DadoErrorMensagem> tratarRegrasDeNegocios(RegrasDeNegocioException exception){
        var mensagem = exception.getMessage();

        return ResponseEntity.badRequest().body(new DadoErrorMensagem(mensagem));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<DadoErrorMensagem> tratarErrorCampoCategoriaInexistente(MethodArgumentTypeMismatchException exception){
        var mensagem = "o valor '" + exception.getValue() + "' Informado não é válido.";

        return ResponseEntity.badRequest().body(new DadoErrorMensagem(mensagem));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<DadoErrorMensagem> tratarErrorRota(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DadoErrorMensagem("A Rota informada não existe"));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DadoErrorMensagem> tratarObjetoNaoEncontradoNoBanco(EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadoErrorMensagem(exception.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DadoErrorMensagem> tratarErrorCredentials(BadCredentialsException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new DadoErrorMensagem(exception.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DadoErrorMensagem> tratarErroEMailExistente(){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new DadoErrorMensagem("Email Já Cadastrado."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DadoErrorMensagem> tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DadoErrorMensagem("Erro interno no servidor. Tente novamente mais tarde."));
    }




}


