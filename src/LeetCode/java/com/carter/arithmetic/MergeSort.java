package com.carter.arithmetic;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSort {
	public static void main(String[] args) {
		int[] arr = { 8, 4, 5, 7, 1, 3, 6, 2 };
		int[] temp = new int[arr.length];
		mergeSort(arr, 0, arr.length - 1, temp);
		System.out.println(Arrays.toString(arr));
	}

	/**
	 * 合并的方法
	 * @param arr:排序的原始数组
	 * @param left:左边有序序列的初始索引
	 * @param mid:中间索引
	 * @param right:右边索引
	 * @param temp:临时数组
	 */
	private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
		int i = left;
		int j = mid + 1;
		int t = 0;//指向temp数组的当前索引

		//先将左右两边（有序）的数据按照规则填充到temp数组，直接左右两边的有序数列，有一边处理完毕为止
		while (i <= mid && j <= right) {
			//如果左边的有序数列的当前元素小于等于右边有序数列的当前元素，就将左边的当前元素拷贝到temp数组，然后后移
			if (arr[i] <= arr[j]) {
				temp[t] = arr[i];
				t += 1;
				i += 1;
			} else {
				//反之，将右边的当前元素拷贝到temp数组，然后后移
				temp[t] = arr[j];
				t += 1;
				j += 1;
			}
		}
		//将有剩余数据的一边数据依次全部填充到temp
		while (i <= mid) {
			//说明左边的有序数列还有剩余的元素，就全部填充到temp
			temp[t] = arr[i];
			t += 1;
			i += 1;
		}
		while (j <= right) {
			//说明左边的有序数列还有剩余的元素，就全部填充到temp
			temp[t] = arr[j];
			t += 1;
			j += 1;
		}
		//将temp数组的元素拷贝到arr
		t = 0;
		int tempLeft = left;
		System.out.println("tempLeft=" + tempLeft + "  right=" + right);
		while (tempLeft <= right) {
			arr[tempLeft] = temp[t];
			t += 1;
			tempLeft += 1;
		}
	}

	/**
	 * 分解的方法
	 * @param arr
	 * @param left
	 * @param right
	 * @param temp
	 */
	private static void mergeSort(int[] arr, int left, int right, int[] temp) {
		if (left < right) {
			int mid = (left + right) / 2;
			//向左递归进行分解
			mergeSort(arr, left, mid, temp);
			//向右递归进行分解
			mergeSort(arr, mid + 1, right, temp);
			//到合并
			merge(arr, left, mid, right, temp);
		}
	}

}
