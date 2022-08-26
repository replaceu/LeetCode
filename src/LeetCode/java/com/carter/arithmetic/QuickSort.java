package com.carter.arithmetic;

import java.util.Arrays;

/**
 * 快速排序（快排）
 * 对冒泡排序的一种改进，通过一趟排序将要排序的数据分割成独立的两个部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后
 * 按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行
 */
public class QuickSort {
	public static void main(String[] args) {
		int[] arr = { 1, 7, 2, 4, 6, 5, 0, 9 };
		quickSort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));

		//		int[] arr = new int[80000];
		//		for (int i = 0; i < 80000; i++) {
		//			arr[i] = (int) (Math.random() * 80000);
		//		}
		//		Date dateBefore = new Date();
		//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//		String dateBeforeStr = simpleDateFormat.format(dateBefore);
		//		System.out.println("排序前的时间是" + dateBeforeStr);
		//
		//		quickSort(arr, 0, arr.length - 1);
		//
		//		Date dateAfter = new Date();
		//		String dateAfterStr = simpleDateFormat.format(dateAfter);
		//		System.out.println("排序前的时间是" + dateAfterStr);

	}

	public static void quickSort(int[] arr, int left, int right) {
		//left与right分别表示数组的最左最右下标
		int pivot = arr[(left + right) / 2];//pivot=0
		int leftPoint = left;//leftPoint=0 arr[leftPoint]=-9
		int rightPoint = right;//rightPoint=5 arr[rightPoint]=70
		int temp = 0;

		while (leftPoint < rightPoint) {

			while (arr[leftPoint] < pivot) {
				leftPoint += 1;
			}
			while (arr[rightPoint] > pivot) {
				rightPoint -= 1;
			}
			//如果leftPoint>=rightPoint说明pivot的左右两边的值，已经全部按照左边小于等于pivot的值，右边大于等于pivot的值排好了
			if (leftPoint >= rightPoint) {
				break;
			}
			//如果没有满足上面的条件就交换位置
			temp = arr[rightPoint];
			arr[rightPoint] = arr[leftPoint];
			arr[leftPoint] = temp;
			//如果交换完成后，发现arr[leftPoint]==pivot，那么就需要前移
			if (arr[leftPoint] == pivot) {
				rightPoint -= 1;
			}
			if (arr[rightPoint] == pivot) {
				leftPoint += 1;
			}
		}

		if (leftPoint == rightPoint) {
			leftPoint += 1;
			rightPoint -= 1;
		}

		//向左递归
		if (left < rightPoint) {
			quickSort(arr, left, rightPoint);
		}
		//向右递归
		if (right > leftPoint) {
			quickSort(arr, leftPoint, right);
		}
	}

}
