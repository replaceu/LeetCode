package com.carter.arithmetic;

import java.util.Arrays;

/**
 * 希尔排序基本思想
 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序，随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1，
 * 整个文件被分成1组，算法便终止
 */
public class ShellSort {
	public static void main(String[] args) {
		int[] arr = { 8, 9, 1, 7, 2, 3, 5, 4, 6, -1 };
		//创建80000个随机的数组
		//		int[] arr = new int[80000];
		//		for (int i = 0; i < 80000; i++) {
		//			arr[i] = (int) (Math.random() * 80000);
		//		}
		//		Date dateBefore = new Date();
		//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//		String dateBeforeStr = simpleDateFormat.format(dateBefore);
		//		System.out.println("排序前的时间是" + dateBeforeStr);
		//
		shellSortMove(arr);
		System.out.println(Arrays.toString(arr));
		//
		//		Date dateAfter = new Date();
		//		String dateAfterStr = simpleDateFormat.format(dateAfter);
		//		System.out.println("排序前的时间是" + dateAfterStr);
	}

	//希尔排序算法的交换法
	public static void shellSortExchange(int[] arr) {
		int temp = 0;
		int count = 0;
		for (int gap = arr.length / 2; gap > 0; gap /= 2) {
			//第一轮：将10个数分成了5组
			for (int i = gap; i < arr.length; i++) {
				//遍历各组中的所有元素（总共gap组，每组个元素），所以步长是gap
				for (int j = i - gap; j >= 0; j -= gap) {
					//如果当前元素大于加上步长之后的那个元素，说明需要交换位置
					if (arr[j] > arr[j + gap]) {
						temp = arr[j];
						arr[j] = arr[j + gap];
						arr[j + gap] = temp;
					}
				}
			}
			//System.out.println("第" + (++count) + "轮输出" + Arrays.toString(arr));
		}

		//		//第二轮
		//		for (int i = 2; i < arr.length; i++) {
		//			//遍历各组中的所有元素（总共5组，每组2个元素），所以步长是5
		//			for (int j = i - 2; j >= 0; j -= 2) {
		//				//如果当前元素大于加上步长之后的那个元素，说明需要交换位置
		//				if (arr[j] > arr[j + 2]) {
		//					temp = arr[j];
		//					arr[j] = arr[j + 2];
		//					arr[j + 2] = temp;
		//				}
		//			}
		//		}
		//		//System.out.println("第" + i + "轮输出");
		//		System.out.println(Arrays.toString(arr));
		//
		//		//第三轮
		//		for (int i = 1; i < arr.length; i++) {
		//			//遍历各组中的所有元素（总共5组，每组2个元素），所以步长是5
		//			for (int j = i - 1; j >= 0; j -= 1) {
		//				//如果当前元素大于加上步长之后的那个元素，说明需要交换位置
		//				if (arr[j] > arr[j + 1]) {
		//					temp = arr[j];
		//					arr[j] = arr[j + 1];
		//					arr[j + 1] = temp;
		//				}
		//			}
		//		}
		//		//System.out.println("第" + i + "轮输出");
		//		System.out.println(Arrays.toString(arr));
	}

	//希尔排序算法的移动法(对交换法的优化)
	public static void shellSortMove(int[] arr) {
		//第一次增量gap等于数组长度的1/2，保证gap大于0，每次gap除以2取整
		for (int gap = arr.length / 2; gap > 0; gap /= 2) {
			//从第gap个元素，逐个对其所在的组进行直接插入排序
			for (int i = gap; i < arr.length; i++) {
				int j = i;
				int temp = arr[j];
				if (arr[j] < arr[j - gap]) {
					while (j - gap >= 0 && temp < arr[j - gap]) {
						//移动gap长度
						arr[j] = arr[j - gap];
						j -= gap;
					}
					arr[j] = temp;
				}
			}
		}
	}

	public static void shellSortByMove(int arr[]) {
		for (int gap = arr.length / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < arr.length; i++) {
				//第一次gap=5
				int j = i;
				int temp = arr[j];//temp = arr[5]=3
				if (arr[j] < arr[j - gap]) {//如果arr[5](3)<arr[0](8)
					while (j - gap >= 0 && temp < arr[j - gap]) {
						arr[j] = arr[j - gap];//arr[5]=arr[0]
						j -= gap;//j=0
					}
					arr[j] = temp;
				}

			}
		}
	}
}
