package com.carter.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个整数可以由连续的自然数之和表示，给定一个证书，计算该整数有几种自然数之和的表达式
 * --滑动窗口
 * --双指针
 */
public class ContinuousNaturalNumSum {
	public static void main(String[] args) {
		int result = getContinuousNaturalNumSum(10);
		System.out.println();
		System.out.println("size:"+result);

	}

	public static int getContinuousNaturalNumSum(int target) {
		int count = 1;
		int[] naturalNumArr = new int[target];
		for (int i = 1; i <= target; i++) {
			naturalNumArr[i - 1] = i;
		}
		int left = 0;
		int right = 0;
		while (right < target) {
			int sum = getSum(naturalNumArr, left, right,target);
			if (sum < target) {
				right++;
			}
			if (sum == target) {
				count++;
				left++;
			}
			if (sum > target) {
				left++;
			}
		}
		return count;
	}

	private static int getSum(int[] naturalNumArr, int left, int right,int target) {
		List<Integer> retList = new ArrayList<>();
		int sum = 0;
		for (int i = left; i < right; i++) {
			sum += naturalNumArr[i];
			retList.add(naturalNumArr[i]);
		}
		if (sum==target){
			for (Integer num : retList) {
				System.out.print(num+"-");
			}
			System.out.println();
		}
		return sum;
	}
}
