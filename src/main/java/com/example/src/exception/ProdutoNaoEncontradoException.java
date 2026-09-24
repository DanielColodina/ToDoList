package com.example.src.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(Long id) {
        super("Produto não encontrado: " + id);
    }

    public ProdutoNaoEncontradoException(String nome) {
        super("Produto não encontrado: " + nome);
    }
}
