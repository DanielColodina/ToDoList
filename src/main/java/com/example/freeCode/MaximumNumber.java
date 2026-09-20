package com.example.freeCode;

public class MaximumNumber {
    public static void main(String[] args) {

        Numeromaximo numeroMaximo = new Numeromaximo();

        int[] nums = {3,7,9,2};
        System.out.println(numeroMaximo.maiornumero(nums));

    }
}
class Numeromaximo {

    public int maiornumero(int[] nums) {

        int maior = nums[0];

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] > maior) {
                maior = nums[i];
            }
        }

        return maior;
    }
}