package com.example.freeCode;

public class ContainsDuplicate {

    public static void main(String[] args) {


        Contains contains = new Contains();

        int[] nums = {9, 2, 3, 1};


        System.out.println("IS present is true or false: " + (contains.results(nums)) );

    }
}

class Contains {

    public boolean results(int[] nums) {
        /*nums = {1,2,3,1} saida é true*/

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {


                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false ;
    }
}