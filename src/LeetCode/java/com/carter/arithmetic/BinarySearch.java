package com.carter.arithmetic;

/**
 * @author Carter
 * @date 2021-09-16 15:54
 * @description:二分法查找,适用于有序数组
 * @version:
 */
public class BinarySearch {
	public static void main(String[] args) {
		int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Integer index = binarySearchRecursion(arr, 0, arr.length, 10);
		System.out.println("查找到的目标值位置" + index);

	}

	public static Integer binarySearch(int[] arr, int key) {
		int high = arr.length - 1;
		int low = 0;

		while (low < high) {
			int mid = (high + low) / 2;
			if (key == arr[mid]) {
				return mid;
			} else if (key > mid) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return -1;

	}

	/**
	 * 二分法查找的递归写法
	 * @param arr
	 * @param key
	 * @return
	 */
	public static Integer binarySearchRecursion(int[] arr, int low, int high, int key) {

		while (low < high) {
			int mid = (high + low) / 2;
			if (key == arr[mid]) {
				return mid;
			} else if (key > arr[mid]) {
				low = mid + 1;
				binarySearchRecursion(arr, low, high, key);
			} else {
				high = mid - 1;
				binarySearchRecursion(arr, low, high, key);
			}
		}
		return -1;
	}
}
