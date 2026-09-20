package com.example.freeCode;


import java.util.ArrayList;

public class FreeLista {

    public static void main(String[] args) {

        Nomes nomes = new Nomes();
        nomes.adicionar();

        /*NUMEROS INTEIROS*/

        Numeros numeros = new Numeros();
        numeros.Inteiro();

    }
}

class Nomes{
    public void adicionar (){


        ArrayList<String> ListaNomes = new ArrayList<>();

        ListaNomes.add("Daniel");
        ListaNomes.add("Fernando");
        ListaNomes.add("Franzino");

        System.out.println(ListaNomes);
    }
}

class Numeros{
    public void Inteiro() {

        ArrayList<Integer> Inteiros = new ArrayList<>();


        Inteiros.add(3);
        Inteiros.add(1);
        Inteiros.add(6);

        System.out.println(Inteiros);
    }
}
