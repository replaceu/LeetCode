package com.carter.arithmetic;

/**
 * 动态规划算法：将大问题划分为小问题进行解决，从而一步步获取最优解
 */
public class DynamicProgram {
	public static void main(String[] args) {
		//物品的重量数组
		int[] weight = { 1, 1, 3 };
		//物品的价值
		int[] value = { 1500, 3000, 2000 };
		//背包的容量
		int maxWeight = 4;
		//物品的个数
		int n = value.length;
		//创建二维数组，value[i][j],表示前i个物品中能够放入容量为j的背包中的最大价值
		getKnapsack(weight, value, maxWeight, n);
		System.out.println("===========================");
		getCompletePack(weight, value, maxWeight, n);
		System.out.println("===========================");
		//int[] limits = { 4, 1, 1 };
		//getManyPack(weight, value, maxWeight, n, limits);

	}

	/*
	todo:每次遍历到的第i个物品，根据w[i]和v[i]来决定是否需要将该物品放入背包中，
	 即对于给定的n个物品，设v[i]，w[i]分别为第i个物品的价值和容量，C是背包的容量。
	 再令v[i][j]表示在前i个物品中能够装入容量为j的背包中最大价值，这是一个0-1背包问题
	*/
	private static void getKnapsack(int[] weight, int[] value, int maxWeight, int n) {
		int[][] val = new int[n + 1][maxWeight + 1];
		//为了记录放入商品的情况，定义一个二维数组
		int[][] path = new int[n + 1][maxWeight + 1];
		//初始化第一行和第一列，默认为0
		for (int i = 0; i < val.length; i++) {
			val[i][0] = 0;
		}
		for (int i = 0; i < val[0].length; i++) {
			val[0][i] = 0;
		}
		for (int i = 1; i < val.length; i++) {
			for (int j = 1; j < val[0].length; j++) {
				if (weight[i - 1] > j) {
					val[i][j] = val[i - 1][j];
				} else {
					int toCompare = value[i - 1] + val[i - 1][j - weight[i - 1]];
					if (val[i - 1][j] < toCompare) {
						val[i][j] = toCompare;
						//将当前情况记录到path
						path[i][j] = 1;
					} else {
						val[i][j] = val[i - 1][j];
					}
				}
			}
		}

		for (int i = 0; i < val.length; i++) {
			for (int j = 0; j < val[i].length; j++) {
				System.out.print(val[i][j] + " ");
			}
			System.out.println();
		}
		//		for (int i = 0; i < path.length; i++) {
		//			for (int j = 0; j < path[i].length; j++) {
		//				System.out.printf("第%d个商品放入到背包\n", i);
		//			}
		//		}
		int i = path.length - 1;
		int j = path[0].length - 1;
		while (i > 0 && j > 0) {
			if (path[i][j] == 1) {
				System.out.printf("第%d个商品放入到背包\n", i);
				j -= weight[i - 1];
			}
			i--;
		}
	}

	/*todo:完全背包问题，即在容量为C的背包中，某个物品可以被放入无限次，
	   而在0-1背包问题中，状态转移方程是value[i-1]+val[i-1][j-weight[i-1]],
	   在完全背包问题中需要注意的是考虑放入一个物品i时应当考虑还可能继续放入物品i,
	   因此状态转移函数应当变为value[i]+val[i][j-weight[i]]*/

	private static void getCompletePack(int[] weight, int[] value, int maxWeight, int n) {
		int[][] val = new int[n + 1][maxWeight + 1];
		int[][] path = new int[n + 1][maxWeight + 1];
		for (int i = 0; i < val.length; i++) {
			val[i][0] = 0;
		}
		for (int i = 0; i < val[0].length; i++) {
			val[0][i] = 0;
		}
		for (int i = 1; i < val.length; i++) {
			for (int j = 1; j < val[0].length; j++) {
				if (weight[i - 1] > j) {
					val[i][j] = val[i - 1][j];
				} else {
					int toCompare = value[i - 1] + val[i][j - weight[i - 1]];
					if (toCompare > val[i - 1][j]) {
						val[i][j] = toCompare;
						path[i][j] = 1;
					} else {
						val[i][j] = val[i - 1][j];
					}
				}
			}
		}
		for (int i = 0; i < val.length; i++) {
			for (int j = 0; j < val[i].length; j++) {
				System.out.print(val[i][j] + " ");
			}
			System.out.println();
		}

		//		for (int i = 0; i < path.length; i++) {
		//			for (int j = 0; j < path[i].length; j++) {
		//				System.out.printf("第%d个商品放入到背包\n", i);
		//			}
		//		}
		int i = path.length - 1;
		int j = path[0].length - 1;
		while (i > 0 && j > 0) {
			if (path[i][j] == 1) {
				System.out.printf("第%d个商品放入到背包\n", i);
				j -= weight[i - 1];
			}
			i--;
		}

	}

	/*todo:多重背包，每类物品都有个数限制，int[] limits表示每类物品的限制数量*/
	public static void getManyPack(int[] weight, int[] value, int maxWeight, int n, int[] limits) {
		int[][] val = new int[n + 1][maxWeight + 1];
		int[][] path = new int[n + 1][maxWeight + 1];
		for (int i = 0; i < val.length; i++) {
			val[i][0] = 0;
		}
		for (int i = 0; i < val[0].length; i++) {
			val[0][i] = 0;
		}
		for (int i = 1; i < val.length; i++) {
			for (int j = 1; j < val[0].length; j++) {
				if (weight[i - 1] > j) {
					val[i][j] = val[i - 1][j];
				} else {
					//todo:考虑物品的件数限制
					int limit = Math.min(limits[i - 1], j / weight[i - 1]);
					for (int k = 0; k < limit + 1; k++) {
						int toCompare = k * value[i - 1] + val[i - 1][j - k * weight[i - 1]];
						val[i][j] = val[i][j] > Math.max(val[i - 1][j], toCompare) ? val[i][j] : Math.max(val[i - 1][j], toCompare);
					}
				}
			}
		}
		for (int i = 0; i < val.length; i++) {
			for (int j = 0; j < val[i].length; j++) {
				System.out.print(val[i][j] + " ");
			}
			System.out.println();
		}

	}

}
