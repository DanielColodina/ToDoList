package com.example.freeCode;



public class TwoSun {

    public static void main(String []args){

    leetCode leetcode = new leetCode();

    int[] nums = {4,1,5,2};
    int target = 7;

    int[] resultado = leetcode.twosun(nums,target);
        System.out.println(resultado[0] + "," + resultado[1]);


    }

}

class leetCode{

    public int[] twosun(int[] nums, int target) {

        /*saida [0,1]*/
        /*Passando numeros*/
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {

                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
        }
    }


