package com.carter.arithmetic;

import java.util.Arrays;

public class BubbleSort {
	public static void main(String[] args) {
		int arr[] = { 3, 9, -1, 10, 0 };
		bubbleSort(arr);

		//		System.out.println("排序后的数组：" + Arrays.toString(arr));
		//		//第二趟排序
		//		for (int i = 0; i < arr.length - 1-1; i++) {
		//			//如果前面的数比后面的数大，就交换
		//			if (arr[i] > arr[i + 1]) {
		//				tmp = arr[i];
		//				arr[i] = arr[i + 1];
		//				arr[i + 1] = tmp;
		//			}
		//		}
		//		System.out.println("排序后的数组：" + Arrays.toString(arr));
		//		//第三趟排序
		//		for (int i = 0; i < arr.length -1-2; i++) {
		//			//如果前面的数比后面的数大，就交换
		//			if (arr[i] > arr[i + 1]) {
		//				tmp = arr[i];
		//				arr[i] = arr[i + 1];
		//				arr[i + 1] = tmp;
		//			}
		//		}
		//		System.out.println("排序后的数组：" + Arrays.toString(arr));
		//		//第四趟排序
		//		for (int i = 0; i < arr.length - 1-3; i++) {
		//			//如果前面的数比后面的数大，就交换
		//			if (arr[i] > arr[i + 1]) {
		//				tmp = arr[i];
		//				arr[i] = arr[i + 1];
		//				arr[i + 1] = tmp;
		//			}
		//		}
		//		System.out.println("排序后的数组：" + Arrays.toString(arr));

	}

	private static void bubbleSort(int[] arr) {
		int tmp = 0;
		boolean flag = false;//是否进行过交换
		//第一趟排序
		for (int j = 0; j < arr.length - 1; j++) {
			for (int i = 0; i < arr.length - 1 - j; i++) {
				//如果前面的数比后面的数大，就交换
				if (arr[i] > arr[i + 1]) {
					flag = true;
					tmp = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = tmp;
				}
			}
			if (flag == false) {
				//在一趟排序中，一次交换都没有发生过
				break;
			} else {
				flag = false;
			}
			System.out.println("排序后的数组：" + Arrays.toString(arr));
		}
	}
	/**
	 * 我们可以看到总共排序了4趟，第一次i < arr.length - 1-0,第二次i < arr.length - 1-1，第三次i < arr.length - 1-2，第四次i < arr.length - 1-3
	 * 所以如果要使用for循环将整个4趟排序包起来，需要引入新的变量j，j从0到3取值，i < arr.length - 1-j
	 */
}
