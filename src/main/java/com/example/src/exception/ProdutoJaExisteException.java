package com.example.src.exception;

public class ProdutoJaExisteException extends RuntimeException {

    public ProdutoJaExisteException(String nome) {
        super("Já existe um produto com o nome: " + nome);
    }

    public ProdutoJaExisteException(String nome, Throwable cause) {
        super("Já existe um produto com o nome: " + nome, cause);
    }
}
