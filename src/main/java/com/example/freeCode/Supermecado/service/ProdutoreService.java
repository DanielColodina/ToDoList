package com.example.freeCode.Supermecado.service;

import com.example.freeCode.Supermecado.model.Produto;
import com.example.freeCode.Supermecado.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoreService {

    @Autowired
    private ProdutoRepository produtoRepository;


    //Crie um contstrutor onde os parametros serão da Classe Produto
    public Produto cadastrar(String nome, Integer quantidade, double valor) {

        Produto produto = new Produto();

        produto.setNome(nome);
        produto.setQuantidade(quantidade);
        produto.setValor(valor);


        return produtoRepository.save(produto);

    }
}

