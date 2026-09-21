package com.example.freeCode;


import lombok.*;

import java.util.ArrayList;
import java.util.Scanner;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {


    private String nome;
    private int quantidade;
    private double valor;

}

class nomeProduto {

    ArrayList<String> nomesProduto = new ArrayList<>();


}
