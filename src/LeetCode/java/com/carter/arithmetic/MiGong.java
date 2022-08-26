package com.carter.arithmetic;

public class MiGong {
	public static void main(String[] args) {
		//模拟迷宫，用二维数组
		int[][] map = new int[8][7];
		//使用1表示墙
		for (int i = 0; i < 7; i++) {
			map[0][i] = 1;
			map[7][i] = 1;
		}
		for (int i = 0; i < 8; i++) {
			map[i][0] = 1;
			map[i][6] = 1;
		}
		map[3][1] = 1;
		map[3][2] = 1;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.printf(map[i][j] + " ");
			}
			System.out.println();
		}

		setWay(map, 1, 1);
		//输出新的地图
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.printf(map[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 如果小球能到map[6][5]表示通路找到，当map[i][j]=0的时候，表示还没有走过，当等于1的时候，表示不能走，当等于2的时候表示走过的通路，当等于3的时候表示该点已经走过，但是走不通
	 * 在走迷宫时，需要确定一个策略：下--右--上--左，如果该点走不通，再回溯
	 * @param map 表示地图
	 * @param i 从哪个位置开始找
	 * @param j 到哪个位置开始找
	 * @return
	 */
	public static boolean setWay(int[][] map, int i, int j) {
		//通路已经找到
		if (map[6][5] == 2) {
			return true;
		} else {
			//当前这个点还没有走过
			if (map[i][j] == 0) {
				//按照策略走,假设这个点可以走通
				map[i][j] = 2;
				if (setWay(map, i + 1, j)) {
					return true;
				} else if (setWay(map, i, j + 1)) {
					return true;
				} else if (setWay(map, i - 1, j)) {
					return true;
				} else if (setWay(map, i, j - 1)) {
					return true;
				} else {
					//该点走不通，置为3
					map[i][j] = 3;
					return false;
				}

			} else {
				return false;
			}
		}
	}

}
