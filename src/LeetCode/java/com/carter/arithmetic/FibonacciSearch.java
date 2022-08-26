package com.carter.arithmetic;

import java.util.Arrays;

/**
 * @author Carter
 * @date 2021-09-17 22:23
 * @description:斐波那契查找
 * @version:
 */
public class FibonacciSearch {
	public static int maxSize = 20;

	public static void main(String[] args) {
		int[] arr = { 1, 8, 10, 89, 1000, 1234, 1235, 1236, 1999, 2000, 3000, 4000, 5001, 5678, 5789, 6000, 6345 };
		int result = fibonacciSearchSort(arr, 6000);

		System.out.println(result);

	}

	//因为后面需要定义mid=low+F(K-1)-1,需要使用到斐波那契数列，因此需要先获取到一个斐波那契数列
	public static int[] fibonacciArr() {
		int[] fibonacciArr = new int[maxSize];
		fibonacciArr[0] = 1;
		fibonacciArr[1] = 1;

		for (int i = 2; i < maxSize; i++) {
			fibonacciArr[i] = fibonacciArr[i - 1] + fibonacciArr[i - 2];
		}
		//System.out.println(fibonacciArr[maxSize - 1]);

		return fibonacciArr;
	}

	/**
	 * 斐波那契查找算法,非递归的方式
	 *
	 * @param arr
	 * @param key
	 * @return
	 */
	public static int fibonacciSearch(int[] arr, int key) {
		int low = 0;
		int high = arr.length - 1;
		int k = 0;//表示斐波那契分割数值的下标
		int mid = 0;

		int[] fibonacciArr = fibonacciArr();
		//获取到斐波那契分割数值的下标
		while (high > fibonacciArr[k] - 1) {
			k++;
		}
		//因为fibonacciArr[k]值可能大于数组arr的长度，因此需要构造一个新的数组并指向数组temp
		int[] temp = Arrays.copyOf(arr, fibonacciArr[k]);
		//实际上需要使用arr数组最后的值填充temp
		for (int i = high + 1; i < temp.length - 1; i++) {
			temp[i] = arr[high];
		}
		//使用while循环处理，找到key
		while (low <= high) {
			mid = low + fibonacciArr[k - 1] - 1;
			if (key < temp[mid]) {
				high = mid - 1;
				k--;//即在fibonacciArr[k-1]的前面继续查找
			} else if (key > temp[mid]) {
				low = mid + 1;
				k -= 2;
				/**
				 * 1.全部元素 = 前面的元素+后面的元素
				 * 2.因为fibonacciArr[i] = fibonacciArr[i - 1] + fibonacciArr[i - 2]
				 * 3.所以在右边有fibonacciArr[i - 2]个元素
				 * 4.所以需要将k-2代入k
				 */
			} else {
				if (mid <= high) {
					return mid;
				} else {
					return high;
				}
			}
		}
		return -1;
	}

	public static int fibonacciSearchSort(int[] arr, int key) {
		int low = 0;
		int high = arr.length - 1;
		int k = 0;//表示斐波那契分割数值的下标
		int mid = 0;

		//得到斐波那契数组
		int[] fibonacciArr = fibonacciArr();

		/**可知上述数据共7个元素，不对应的斐波那契数列中任何F(n),
		 * 此时，策略是采用“大于数组长度的最近一个斐波那契数值”。
		 * 比如当前数组长度为7，斐波那契数列中大于7的最近元素为8*/
		while (high > fibonacciArr[k] - 1) {
			k++;
		}

		//得到一个新的临时数组，长度为fibonacciArr[k]
		int[] temp = Arrays.copyOf(arr, fibonacciArr[k]);

		//新的临时疏忽组从k+1位开始使用原数组arr的最大值来填充
		for (int i = high + 1; i < temp.length; i++) {
			temp[i] = arr[arr.length - 1];
		}
		//开始二分法
		while (low <= high) {

			//此时数组被分割为左右两个区间，左边区间含有F(n-1)个元素，-1是因为下标从0开始（比如F(1)表示两个元素）。
			mid = low + fibonacciArr[k - 1] - 1;

			if (temp[mid] > key) {
				high = mid - 1;
				k--;
			} else if (temp[mid] < key) {
				low = mid + 1;
				k -= 2;
			} else {
				return mid <= high ? mid : high;
			}
		}

		return -1;
	}
}
