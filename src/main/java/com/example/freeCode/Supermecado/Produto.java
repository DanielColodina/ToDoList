package com.example.freeCode.Supermecado;


import com.example.freeCode.Supermecado.model.Produto;

import java.util.ArrayList;
import java.util.Scanner;

class DadosEntrada{
    Scanner scanner = new Scanner(System.in);

}

class nomeProduto {

    private ArrayList<Produto> nomesProduto = new ArrayList<>();

    public String ProdutoComNome(Scanner scanner){


        System.out.println("Qual o nome do produto ?");
        String nome = scanner.nextLine();



        if (nomesProduto.contains(nome)) {
            return "Esse produto já existe";
        } else {

            Produto produto = new Produto();

            produto.setNome(nome);
            nomesProduto.add(produto);
            return "Produto cadastrado";

        }
    }

}
