package com.example.src.exception;

public class OllamaIndisponivelException extends RuntimeException {

    public OllamaIndisponivelException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
