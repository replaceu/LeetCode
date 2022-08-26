package com.carter.arithmetic;

import java.util.Arrays;

public class FloydAlgorithm {
	public static void main(String[] args) {
		char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
		int[][] matrix = new int[vertex.length][vertex.length];
		final int N = 999999;
		matrix[0] = new int[] { 0, 5, 7, N, N, N, 2 };
		matrix[1] = new int[] { 5, 0, N, 9, N, N, 3 };
		matrix[2] = new int[] { 7, N, 0, N, 8, N, N };
		matrix[3] = new int[] { N, 9, N, 0, N, 4, N };
		matrix[4] = new int[] { N, N, 8, N, 0, 5, 4 };
		matrix[5] = new int[] { N, N, N, 4, 5, 0, 6 };
		matrix[6] = new int[] { 2, 3, N, N, 4, 6, 0 };

		//创建Graph对象
		FloydGraph graph = new FloydGraph(vertex.length, matrix, vertex);
		//调用佛罗伊德算法
		graph.floyd();
		graph.show();
	}
}

class FloydGraph {
	//存在顶点的数值
	private char[] vertex;
	//保存从各个顶点出发到其他顶点的距离
	private int[][] distance;
	//保存到达目标顶点的前驱顶点
	private int[][] pre;

	//构造器
	public FloydGraph(int length, int[][] matrix, char[] vertex) {
		this.vertex = vertex;
		this.distance = matrix;
		this.pre = new int[length][length];
		//对pre数组初始化，注意存放的是前驱顶点的下标
		for (int i = 0; i < length; i++) {
			Arrays.fill(pre[i], i);
		}
	}

	//显示pre数组和distance数组
	public void show() {
		//为了显示便于阅读，优化一下输出
		char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
		for (int i = 0; i < distance.length; i++) {
			//先将pre数组输出的一行
			for (int j = 0; j < distance.length; j++) {
				System.out.print(vertex[pre[i][j]] + " ");
			}
			System.out.println();
			//输出distance数组的一行
			for (int j = 0; j < distance.length; j++) {
				System.out.print("(" + vertex[i] + "到" + vertex[j] + "的最短路径是" + distance[i][j] + ")");
			}
			System.out.println();
			System.out.println();
		}
	}

	//佛罗伊德算法，比较容易理解，而且容易实现
	public void floyd() {
		//变量保存距离
		int len = 0;
		//对中间顶点遍历
		for (int k = 0; k < distance.length; k++) {
			//从i顶点开始出发[A,B.C,D,E,F,G]
			for (int i = 0; i < distance.length; i++) {
				for (int j = 0; j < distance.length; j++) {
					//求出从i顶点出发，经过k中间顶点，达到j顶点距离
					len = distance[i][k] + distance[k][j];
					if (len < distance[i][j]) {
						//更新距离
						distance[i][j] = len;
						//更新前驱顶点
						pre[i][j] = pre[k][j];
					}
				}
			}
		}
	}

}
