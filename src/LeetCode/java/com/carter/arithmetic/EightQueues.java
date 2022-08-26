package com.carter.arithmetic;

/**
 * 八皇后问题思路
 * 1.第一个皇后先放在第一行第一列
 * 2.第二个皇后放在第二行第一列，然后判断是否OK，如果不OK，就继续放在第二列，第三列，...直到所有列都放完，找到一个合适
 * 3.以此类推到所有的行，当所有皇后都不冲突，算是找到个正确解
 * 4.当得到一个正确解时，在栈退回到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解全部得到
 * 5.然后回头继续第一个皇后放在第二列，后面继续执行1,2,3,4的步骤
 */

public class EightQueues {
	//定义一个max表示多少个皇后
	int maxSize = 8;

	//定义一个数组，保存皇后位置的结果
	int[]		locationArray	= new int[maxSize];
	static int	count			= 0;

	public static void main(String[] args) {
		EightQueues queues = new EightQueues();
		queues.check(0);
		System.out.printf("一共有%d种解法：", count);

	}

	//将皇后摆放的位置打印出来
	private void print() {
		count++;
		for (int i = 0; i < locationArray.length; i++) {
			System.out.printf(locationArray[i] + " ");
		}
		System.out.println();
	}

	/**
	 * 判断皇后位置是否与之前的皇后冲突
	 * @param n 表示第n个皇后
	 * @return
	 */
	private boolean judge(int n) {
		for (int i = 0; i < n; i++) {
			//判断第n个皇后是不是与之前的n-1个皇后在同一列或者第n个皇后是否与第i个皇后在同一斜线
			if (locationArray[i] == locationArray[n] || Math.abs(n - i) == Math.abs(locationArray[n] - locationArray[i])) { return false; }
		}
		return true;
	}

	/**
	 * 编写一个方法，放置第n个皇后,特别注意：check是每一次递归时，都有for循环，因此会有回溯
	 * @param n
	 */
	private void check(int n) {

		//说明已经放到最后一个皇后，其实8个皇后已经放置好了
		if (n == maxSize) {
			print();
			return;
		}
		//依次放入皇后，并判断是否冲突
		for (int i = 0; i < maxSize; i++) {
			//先将当前这个皇后n，放到该行的第一列
			locationArray[n] = i;
			//判断当放置第n个皇后到第i列时，是否冲突
			if (judge(n)) {
				//如果不冲突就接着放第n+1个皇后
				check(n + 1);
			}
			//如果冲突，就继续执行array[n]=i,即将第n个皇后，放置在本行后移的一个位置
		}
	}
}
