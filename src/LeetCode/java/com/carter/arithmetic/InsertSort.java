package com.carter.arithmetic;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertSort {
	public static void main(String[] args) {
		//int arr[] = { 101, 34, 119, 1 };

		int[] arr = new int[80000];
		for (int i = 0; i < 80000; i++) {
			arr[i] = (int) (Math.random() * 80000);
		}
		Date dateBefore = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateBeforeStr = simpleDateFormat.format(dateBefore);
		System.out.println("排序前的时间是" + dateBeforeStr);

		insertSort(arr);

		Date dateAfter = new Date();
		String dateAfterStr = simpleDateFormat.format(dateAfter);
		System.out.println("排序前的时间是" + dateAfterStr);
	}

	/**
	 * 插入排序
	 * 将原先的数组看成两个新数组，将原来的数组依次找到合适的位置放到新数组当中，这样新的数组就是有序数组了
	 * @param arr
	 */
	public static void insertSort(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			int insertValue = arr[i];//34
			int insertIndex = i - 1;
			/**
			 * 1.insertIndex>=0,保证给insertValue找到适当位置，不越界
			 * 2.insertValue<arr[insertIndex]说明待插入的值还没有找适当的位置
			 * 3.就需要将arr[insertIndex]后移
			 */
			while (insertIndex >= 0 && insertValue < arr[insertIndex]) {//34<arr[0](101)
				arr[insertIndex + 1] = arr[insertIndex];//arr[1]=arr[0]=101
				insertIndex--;//insertIndex=-1(退出)
			}
			//退出循环，说明插入的位置已经找到
			arr[insertIndex + 1] = insertValue;//arr[0]=34

			//System.out.println("第" + i + "轮输出");
			//System.out.println(Arrays.toString(arr));

		}

		//第一轮

		//		//第二轮
		//		insertValue = arr[2];//34
		//		insertIndex = 2 - 1;
		//		/**
		//		 * 1.insertIndex>=0,保证给insertValue找到适当位置，不越界
		//		 * 2.insertValue<arr[insertIndex]说明待插入的值还没有找适当的位置
		//		 * 3.就需要将arr[insertIndex]后移
		//		 */
		//		while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
		//			arr[insertIndex + 1] = arr[insertIndex];
		//			insertIndex--;
		//		}
		//		//退出循环，说明插入的位置已经找到
		//		arr[insertIndex + 1] = insertValue;
	}
}
