package com.carter.arithmetic;

import java.util.Arrays;

/**
 * 给定一个长度为 n 的可能有重复值的数组，
 * 找出其中不去重的最小的 k 个数。例如数组
 * 元素是4,5,1,6,2,7,3,8这8个数字，
 * 则最小的4个数字是1,2,3,4(任意顺序皆可)
 */
public class GetLeastNumbers {

	public static void getLeastNumbers(int[] arr, int k, int left, int right) {
		int leftPoint = left;
		int rightPoint = right;
		int temp = 0;
		while (leftPoint < rightPoint) {
			if (arr[leftPoint] < arr[k]) {
				leftPoint++;
			}
			if (arr[rightPoint] > arr[k]) {
				rightPoint--;
			}
			if (leftPoint >= rightPoint) {
				break;
			}
			temp = arr[leftPoint];
			arr[leftPoint] = arr[rightPoint];
			arr[rightPoint] = temp;

		}
		//getLeastNumbers(arr, k - 1);
		if (leftPoint == rightPoint) {
			leftPoint += 1;
			rightPoint -= 1;
		}
		//getLeastNumbers(arr, 4);
		if (left < rightPoint) {
			getLeastNumbers(arr, k, left, right - 1);
		}
		if (right > leftPoint) {
			getLeastNumbers(arr, k, left + 1, right);
		}
	}

	public static void main(String[] args) {
		int[] arr = { -2, 5, 4, 8, 7, 1, 0, 6, -1, 10, -5 };
		getLeastNumbers(arr, 5, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
	}
}
