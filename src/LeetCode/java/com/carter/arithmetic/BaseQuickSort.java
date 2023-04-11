package com.carter.arithmetic;

import java.util.Arrays;

public class BaseQuickSort {
    public static void main(String[] args) {
        int[] arr = { 1, 7, 2, 4, 6, 5, 0, 9 };
        baseQuickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void baseQuickSort(int[] arr, int left, int right) {
        int pivot = arr[left+right]/2;
        int leftPoint =left;
        int rightPoint = right;
        int temp = 0;
        while (leftPoint<rightPoint){
       
        }
    }

}
