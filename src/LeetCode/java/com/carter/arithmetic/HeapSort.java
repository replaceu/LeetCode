package com.carter.arithmetic;

/**
 * 堆排序
 */
public class HeapSort {
	//todo:1.将无序序列构建成一个堆，根据升序降序需求选择大顶堆或者小顶堆（一般升序采用大顶堆，降序采用小顶堆）
	//todo:2.将栈顶元素和末尾元素交换，将最大元素“沉”到数组末端，此时的最大元素将不再参与到后续的调整中
	//todo:3.重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序
	public static void main(String[] args) {
		//要求将数组进行升序排
		int arr[] = { 4, 6, 8, 5, 9 };
		heapSort(arr);
	}

	//todo:编写一个堆排序的方法
	private static void heapSort(int[] arr) {
		int temp = 0;

		//todo:将无序数列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
		for (int i = arr.length / 2 - 1; i >= 0; i--) {
			adjustHeap(arr, i, arr.length);
		}
		//todo:将栈顶元素与末尾元素交换，将最大元素“沉”到数组末端
		for (int j = arr.length - 1; j > 0; j--) {
			//交换
			temp = arr[j];
			arr[j] = arr[0];
			arr[0] = temp;
			adjustHeap(arr, 0, j);
		}
	}

	private static void adjustHeap(int[] arr, int i, int length) {
		//todo:先取出当前元素的值，保存在临时变量
		int temp = arr[i];
		//todo:k=2*i+1，k是i节点的左子节点
		for (int k = 2 * i + 1; k < length; k++) {
			//左子节点的值小于右子节点的值
			if (k + 1 < length && arr[k] < arr[k + 1]) {
				//k指向右子节点
				k++;
			}
			if (arr[k] > temp) {
				//如果子节点大于父节点，这时将较大的值赋值给当前节点
				arr[i] = arr[k];
				//todo:这时的i指向k，继续循环比较
				i = k;
			} else {
				break;
			}
		}
		//todo:当for循环结束后，我们已经将以i为父节点的树的最大值，放在了最顶端（局部）
		arr[i] = temp;
	}
}
