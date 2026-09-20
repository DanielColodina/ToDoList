package com.example.freeCode;

public class BestTimeToBuy {

    public static void main(String[] args) {
        Results results = new Results();

        int[] prices = {2,1,3,4};

        System.out.println(results.maxProfit(prices));
    }


}

class Results {

    public int maxProfit(int[] prices) {

        int maiorLucro = 0;

        for (int i = 0; i < prices.length; i++) {

            for (int j = i + 1; j < prices.length; j++) {

                int lucro = prices[j] - prices[i];

                if (lucro > maiorLucro) {
                    maiorLucro = lucro;
                }
            }
        }

        return maiorLucro;
    }
}