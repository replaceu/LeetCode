package com.carter.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Graph {
	//存储顶点集合
	private List<String> vertexList;

	//存储图对应的邻接矩阵
	private int[][] edges;

	//表示边的数目
	private int numOfEdges;

	//给定数组，记录某个节点是否被访问
	private boolean[] isVisited;

	//构造器
	public Graph(int n) {
		//初始化矩阵和vertexList
		edges = new int[n][n];
		vertexList = new ArrayList<String>(n);
		numOfEdges = 0;
	}

	//插入节点的方法
	public void insertVertex(String vertex) {
		vertexList.add(vertex);
	}

	/**
	 * 添加边的方法
	 * @param v1 表示点的下标，即第几个顶点
	 * @param v2 表示第二个顶点对应的下标
	 * @param weight
	 */
	public void insertEdge(int v1, int v2, int weight) {
		edges[v1][v2] = weight;
		edges[v2][v1] = weight;
		numOfEdges++;
	}

	/**
	 * 广度优先搜索
	 * @param isVisited
	 * @param start
	 */
	private void breadthFirstSearch(boolean[] isVisited, int start) {
		int u;//表示队列的头节点对应的下标
		int w;//邻接节点w
		//初始化队列，记录节点访问的顺序
		LinkedList<Integer> queue = new LinkedList<>();
		//访问节点，输出节点信息
		System.out.print(getValueByIndex(start) + "->");
		//标记为已访问
		isVisited[start] = true;
		//将节点加入队列
		queue.add(start);

		while (!queue.isEmpty()) {
			//取出队列的头节点下标
			u = (Integer) queue.removeFirst();
			//得到第一个紧邻节点的下标
			w = getFirstNeighbor(u);
			while (w != -1) {
				if (!isVisited[w]) {
					System.out.print(getValueByIndex(w) + "->");
					//标记为已经访问
					isVisited[w] = true;
					//加入队列
					queue.addLast(w);
				}
				//以u为前驱点，找w后面的下一个邻接点
				w = getNextNeighbor(u, w);
			}
		}
	}

	//方法重载，遍历所有的点都进行广度优先搜索
	public void breadthFirstSearch() {
		isVisited = new boolean[vertexList.size()];
		for (int i = 0; i < getNumOfVertex(); i++) {
			if (!isVisited[i]) {
				breadthFirstSearch(isVisited, i);
			}
		}
	}

	/**
	 * 深度优先搜索
	 * @param isVisited
	 * @param start
	 */
	private void depthFirstSearch(boolean[] isVisited, int start) {
		//首先我们访问该节点输出
		System.out.print(getValueByIndex(start) + "->");
		//将节点设置为已经访问
		isVisited[start] = true;
		//查找节点i的第一个邻接节点
		int neighbor = getFirstNeighbor(start);
		while (neighbor != -1) {
			//如果存在邻接节点neighbor,就递归寻找下一个
			if (!isVisited[neighbor]) {
				depthFirstSearch(isVisited, neighbor);
			}
			//如果邻接的节点已经被访问过,找到下一个邻接节点
			neighbor = getNextNeighbor(start, neighbor);
		}
	}

	/**
	 * 从start顶点出发，到图里各个顶点的最短路径
	 * @param edges
	 * @param start
	 * @return
	 */
	public int[] getMinPath(int[][] edges, int start) {
		//该张图的节点个数
		int numOfNode = edges.length;
		//声明一个队列
		LinkedList<Integer> queue = new LinkedList<>();
		queue.offer(start);
		int path = 0;
		int[] nodePath = new int[numOfNode + 1];
		for (int i = 0; i < nodePath.length; i++) {
			nodePath[i] = numOfNode;
		}
		nodePath[start] = 0;
		int inCount = 1;
		int outCount = 0;
		int tempCount = 0;

		while (path < numOfNode) {
			path++;
			while (inCount > outCount) {
				Integer nodeIndex = queue.poll();
				outCount++;

				for (int i = 0; i < numOfNode; i++) {
					if (edges[nodeIndex - 1][i] == 1 && nodePath[i + 1] == numOfNode) {
						queue.offer(i + 1);
						tempCount++;
						nodePath[i + 1] = path;
					}
				}
			}
			inCount = tempCount;
			tempCount = 0;
			outCount = 0;
		}
		return nodePath;

	}

	private int getNextNeighbor(int v1, int v2) {
		for (int i = v2 + 1; i < vertexList.size(); i++) {
			if (edges[v1][i] > 0) { return i; }
		}
		return -1;
	}

	private int getFirstNeighbor(int index) {
		for (int j = 0; j < vertexList.size(); j++) {
			if (edges[index][j] > 0) { return j; }
		}
		return -1;
	}

	private String getValueByIndex(int i) {
		return vertexList.get(i);
	}

	//对于depthFirstSearch做一个重载，遍历所有的节点，并且全部深度优先查找
	public void depthFirstSearch() {
		isVisited = new boolean[vertexList.size()];
		//遍历所有的节点，进行depthFirstSearch回溯
		for (int i = 0; i < getNumOfVertex(); i++) {
			if (!isVisited[i]) {
				depthFirstSearch(isVisited, i);
			}
		}
	}

	private int getNumOfVertex() {
		return vertexList.size();
	}

	public static void main(String[] args) {
		int n = 8;
		String vertex[] = { "1", "2", "3", "4", "5", "6", "7", "8" };

		Graph graph = new Graph(n);
		for (String ver : vertex) {
			graph.insertVertex(ver);
		}

		graph.insertEdge(0, 1, 1);
		graph.insertEdge(0, 2, 1);
		graph.insertEdge(1, 3, 1);
		graph.insertEdge(1, 4, 1);
		graph.insertEdge(3, 7, 1);
		graph.insertEdge(4, 7, 1);
		graph.insertEdge(2, 5, 1);
		graph.insertEdge(2, 6, 1);
		graph.insertEdge(5, 6, 1);

		graph.showGraph();

				System.out.println("深度优先搜索");
				graph.depthFirstSearch(); // A->B->C->D->E [1->2->4->8->5->3->6->7]
		//		System.out.println("广度优先搜索");
		//		graph.broadFirstSearch();

		System.out.println("寻求最短距离");
		int[] minPath = graph.getMinPath(graph.edges, 5);
		System.out.println(Arrays.toString(minPath));

	}

	private void showGraph() {
		for (int[] link : edges) {
			System.out.println(Arrays.toString(link));
		}
	}

}
