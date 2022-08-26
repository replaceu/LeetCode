package com.carter.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一棵由n个顶点组成的无向树，顶点编号从1到 n。青蛙从顶点1开始起跳。规则如下：
 *     在一秒内,青蛙从它所在的当前顶点跳到另一个未访问过的顶点（如果它们直接相连）。
 *     青蛙无法跳回已经访问过的顶点。
 *     如果青蛙可以跳到多个不同顶点，那么它跳到其中任意一个顶点上的机率都相同。
 *     如果青蛙不能跳到任何未访问过的顶点上，那么它每次跳跃都会停留在原地。
 * 无向树的边用数组edges描述，其中edges[i]= [fromi,toi] 意味着存在一条直接连通fromi 和 toi 两个顶点的边。
 *
 * 返回青蛙在 t 秒后位于目标顶点 target 上的概率。
 */
public class FrogPosition {
	//存储顶点集合
	private List<String> vertexList;

	//存储图对应的邻接矩阵
	private int[][] edges;

	//表示边的数目
	private int numOfEdges;

	//给定数组，记录某个节点是否被访问
	private boolean[] isVisited;



	public double getFrogPosition(List<String> vertexList,int n) {
		edges = new int[n][n];
		insertEdges(0,1,1);
		insertEdges(0,2,1);
		insertEdges(0,6,1);
		insertEdges(1,3,1);
		insertEdges(1,5,1);
		insertEdges(2,4,1);

		getDepthFirstSearch(vertexList);
		return 0.0;
	}


	public static void main(String[] args) {
		FrogPosition frogPosition = new FrogPosition();

		List<String> vertexList = new ArrayList<>();
		String vertex[] = { "1", "2", "3", "4", "5", "6", "7"};

		for (String s : vertex) {
			vertexList.add(s);
		}


		frogPosition.getFrogPosition(vertexList,7);
	}

	public void insertEdges(int v1,int v2,int weight){
		edges[v1][v2] = weight;
		edges[v2][v1] = weight;
		numOfEdges++;
	}

	public void getDepthFirstSearch(List<String> vertexList){
		isVisited = new boolean[vertexList.size()];
		for (int i = 0; i < vertexList.size(); i++) {
			if (!isVisited[i]){
				depthFirstSearch(isVisited,i,vertexList);
			}
		}
	}

	private void depthFirstSearch(boolean[] isVisited, int start,List<String> vertexList) {

		System.out.print(vertexList.get(start)+"->");
		//todo:将该节点置为已经访问
		isVisited[start] = true;
		//todo:查找节点i的第一个紧邻节点
		int neighbor = getFirstNeighbor(start,vertexList);
		while (neighbor!=-1){
			//todo:如果紧邻接点还没有被访问过，就递归寻找下一个
			if (!isVisited[neighbor]){
				depthFirstSearch(isVisited,neighbor,vertexList);
			}
			//todo:如果紧邻的节点已经访问被访问过了，那么就找到下一个紧邻节点重新递归
			neighbor = getNextNeighbor(start,neighbor,vertexList);
		}
	}

	private int getNextNeighbor(int start, int neighbor,List<String> vertexList) {
		for (int nextNeighbor = neighbor+1; nextNeighbor < vertexList.size(); nextNeighbor++) {
			if (edges[start][nextNeighbor]>0){
				return nextNeighbor;
			}
		}
		return -1;
	}

	/**
	 * 查找节点的紧邻节点
	 * @param index
	 * @return
	 */
	private int getFirstNeighbor(int index,List<String> vertexList) {
		for (int i = 0; i < vertexList.size(); i++) {
			if (edges[index][i]>0){
				return i;
			}
		}
		return -1;
	}

}

