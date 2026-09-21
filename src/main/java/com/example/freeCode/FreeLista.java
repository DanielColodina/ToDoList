package com.example.freeCode;


import com.example.todolist.todolistDois.model.Lista;

import java.util.ArrayList;
import java.util.Scanner;

public class FreeLista {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Nomes nomes = new Nomes();
        nomes.adicionar(scanner);

        /*NUMEROS INTEIROS*/

        Numeros numeros = new Numeros();
        numeros.Inteiro(scanner);

        scanner.close();
    }
}

class Nomes{
    public void adicionar (Scanner scanner){

        ArrayList<String> ListaNomes = new ArrayList<>();

        System.out.print("Digite um nome de Produto: ");
        String nome = scanner.nextLine();

        ListaNomes.add(nome);

        System.out.println(ListaNomes);
    }
}

class Numeros{
    public void Inteiro(Scanner scanner) {

        ArrayList<Integer> Inteiros = new ArrayList<>();

        System.out.print("Digite a quantidade: ");
        Integer numsINT = scanner.nextInt();

        Inteiros.add(numsINT);


        System.out.println(Inteiros);
    }
}
