package com.example.src.exception;

public class ComandoInvalidoException extends RuntimeException {

    public ComandoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public ComandoInvalidoException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
