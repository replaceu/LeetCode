package com.carter.arithmetic;

//todo:给定一个正整数 N，试求有多少组连续正整数满足所有数字之和为 N
public class ConsecutiveNumbersSum {
	public static void main(String[] args) {
		int result = getConsecutiveNumbersSum(9, 0, 1);
		System.out.println(result);

	}

	// 6 = 6 = 1 + 2 + 3
	// 9 = 9 = 4 + 5 = 2 + 3 + 4
	//15 = 15 = 8 + 7 = 4 + 5 + 6 = 1 + 2 + 3 + 4 + 5
	//25 = 25 = 12 + 13 = 3 + 4 + 5 + 6 + 7
	//33 = 33 = 10 + 11 +12 = 16 + 17 = 3 + 4 + 5 + 6 + 7 + 8

	public static int getConsecutiveNumbersSum(int n, int size, int arrLength) {
		int[] arr = new int[arrLength];
		while (arrLength < n) {
			if (arrLength == 1) {
				size++;
				arrLength++;
			} else if (arrLength >= 2) {
				if (n % arrLength == 0) {
					double target = (Math.floor(n / arrLength) + Math.floor(n / arrLength)) * arrLength / 2;
					if (target == n) {
						size++;
						arrLength++;
					} else {
						arrLength++;
					}
				} else {
					double target = (Math.floor(n / arrLength) + Math.floor(n / arrLength) + 1) * arrLength / 2;
					if (target == n) {
						size++;
						arrLength++;
					} else {
						arrLength++;
					}

				}

			}
		}
		return size;
	}
}
