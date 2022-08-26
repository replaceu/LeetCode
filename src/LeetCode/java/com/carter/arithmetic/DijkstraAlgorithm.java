package com.carter.arithmetic;

import java.util.Arrays;

/**
 * 迪杰斯特拉算法
 */
public class DijkstraAlgorithm {
	public static void main(String[] args) {
		char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
		int[][] matrix = new int[vertex.length][vertex.length];
		final int N = 65535;
		matrix[0] = new int[] { 0, 5, 7, N, N, N, 2 };
		matrix[1] = new int[] { 5, 0, N, 9, N, N, 3 };
		matrix[2] = new int[] { 7, N, 0, N, 8, N, N };
		matrix[3] = new int[] { N, 9, N, 0, N, 4, N };
		matrix[4] = new int[] { N, N, 8, N, 0, 5, 4 };
		matrix[5] = new int[] { N, N, N, 4, 5, 0, 6 };
		matrix[6] = new int[] { 2, 3, N, N, 4, 6, 0 };
		//创建Graph对象
		DijkstraGraph graph = new DijkstraGraph(vertex, matrix);
		//graph.showGraph();
		//todo:测试迪杰斯特拉算法
		graph.dijkstra(0);
		graph.showDijkstra();

	}
}

class DijkstraGraph {
	//顶点数组
	private char[] vertex;
	//邻接矩阵
	private int[][] matrix;
	//已经访问的顶点的集合
	private VisitedVertex visitedVertex;

	//构造器
	public DijkstraGraph(char[] vertex, int[][] matrix) {
		this.vertex = vertex;
		this.matrix = matrix;
	}

	//显示结果
	public void showDijkstra() {
		visitedVertex.show();
	}

	//显示图
	public void showGraph() {
		for (int[] line : matrix) {
			System.out.println(Arrays.toString(line));
		}
	}

	//todo:迪杰斯特拉算法实现
	public void dijkstra(int index) {
		visitedVertex = new VisitedVertex(vertex.length, index);
		//更新index顶点到周围顶点的距离和前驱顶点
		update(index);
		for (int i = 0; i < vertex.length; i++) {
			//todo:用于找到距离出发点最近的那个点的下标
			index = visitedVertex.updateArr();
			update(index);
		}
	}

	private void update(int index) {
		int len = 0;
		for (int i = 0; i < matrix[index].length; i++) {
			//todo：len的含义是出发顶点到index顶点的距离+从index顶点到i顶点的距离之和
			len = visitedVertex.getDistance(index) + matrix[index][i];
			//todo:如果i顶点没有被访问过，并且len小于出发顶点到j顶点的距离，那么就需要更新
			if (!visitedVertex.isVisited(i) && len < visitedVertex.getDistance(i)) {
				//todo：visitedVertex更新j顶点的前驱为index顶点
				visitedVertex.updatePreVisited(i, index);
				//todo：更新出发顶点到i顶点的距离
				visitedVertex.updateDistance(i, len);
			}
		}
	}
}

class VisitedVertex {
	//记录各个顶点是否被访问过,1表示访问过，0表示还未访问
	public int[] alreadyArr;
	//每一个下标对应的值为前一个顶点下标，会动态更新
	public int[] preVisited;
	//记录出发顶点到其他所有顶点的距离
	public int[] distance;

	//构造器
	public VisitedVertex(int length, int index) {
		this.alreadyArr = new int[length];
		this.preVisited = new int[length];
		this.distance = new int[length];
		//初始化distance数组
		Arrays.fill(distance, 65535);
		//设置出发顶点被访问过
		this.alreadyArr[index] = 1;
		//设置出发顶点的距离为0
		this.distance[index] = 0;
	}

	//判断index顶点是否被访问过
	public boolean isVisited(int index) {
		return alreadyArr[index] == 1;
	}

	//更新出发顶点到index顶点的距离
	public void updateDistance(int index, int length) {
		distance[index] = length;
	}

	//更新pre这个顶点的前驱顶点为index顶点
	public void updatePreVisited(int pre, int index) {
		preVisited[pre] = index;
	}

	//返回出发顶点到index顶点的距离
	public int getDistance(int index) {
		return distance[index];
	}

	//继续选择并访问新的访问顶点，比如G完后，就是A作为新的访问顶点（不是出发顶点）
	public int updateArr() {
		int min = 65535, index = 0;
		for (int i = 0; i < alreadyArr.length; i++) {
			if (alreadyArr[i] == 0 && distance[i] < min) {
				min = distance[i];
				index = i;
			}
		}
		//更新index顶点被访问过
		alreadyArr[index] = 1;
		return index;
	}

	//显示最后的结果
	public void show() {
		System.out.println("-----------------------------");
		//输出alreadyArr
		for (int i : alreadyArr) {
			System.out.print(i + " ");
		}
		System.out.println();
		//输出preVisited
		for (int i : preVisited) {
			System.out.print(i + " ");
		}
		System.out.println();
		//输出distance
		char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
		int count = 0;
		for (int i : distance) {
			if (i != 65535) {
				System.out.print(vertex[count] + "(" + i + ") ");
			} else {
				System.out.println("N");
			}
			count++;

		}
		System.out.println();
	}

}
