package com.example.src.exception;

import com.example.src.dto.ApiErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErroResponse> tratarValidacao(
            MethodArgumentNotValidException exception) {

        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .orElse("Requisição inválida");

        return resposta(HttpStatus.BAD_REQUEST, mensagem);
    }

    @ExceptionHandler({IllegalArgumentException.class, ComandoInvalidoException.class})
    public ResponseEntity<ApiErroResponse> tratarRequisicaoInvalida(
            RuntimeException exception) {
        return resposta(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ApiErroResponse> tratarProdutoNaoEncontrado(
            ProdutoNaoEncontradoException exception) {
        return resposta(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ProdutoJaExisteException.class)
    public ResponseEntity<ApiErroResponse> tratarProdutoDuplicado(
            ProdutoJaExisteException exception) {
        return resposta(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(OllamaIndisponivelException.class)
    public ResponseEntity<ApiErroResponse> tratarOllamaIndisponivel(
            OllamaIndisponivelException exception) {
        return resposta(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
    }

    private ResponseEntity<ApiErroResponse> resposta(
            HttpStatus status,
            String mensagem) {

        ApiErroResponse erro = new ApiErroResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                mensagem
        );

        return ResponseEntity.status(status).body(erro);
    }
}
