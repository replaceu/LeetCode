package com.carter.arithmetic;

import java.util.Arrays;

public class SelectSort {
	public static void main(String[] args) {
		int arr[] = { 101, 34, 119, 1 };
		select(arr);
	}

	/**
	 * 选择排序
	 * 第一轮。在数组当中先找到一个最小值，与原数组第一个位置的数进行交换
	 * @param arr
	 */
	public static void select(int[] arr) {
		int minIndex = 0;
		int tmp = 0;

		for (int j = 0; j < arr.length - 1; j++) {
			for (int i = j + 1; i < arr.length; i++) {
				if (arr[i] < arr[j]) {
					minIndex = i;
					tmp = arr[minIndex];
					arr[minIndex] = arr[j];
					arr[j] = tmp;

				}
			}
			System.out.println("第" + j + "轮输出");
			System.out.println(Arrays.toString(arr));

		}

	}
}
