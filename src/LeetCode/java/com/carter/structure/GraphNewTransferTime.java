package com.carter.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * 有 M*N 的节点短阵，每个节点可以向8个方向(上、下、左、右及四个斜线方向)转发
 * 数据包，每个节点转发时会消耗固定时延，连续两个相同时延可以减少一个时延值(即当有
 * K 个相同时延的节点连续转发时可以减少 K-1 个时延值)，
 * 求左上角 (0，0)开始转发数据包到右下角 (M-1，N- 1)并转发出的最短时延
 */
public class GraphNewTransferTime {
	public static final int[][]	directions	= { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 1 }, { 1, 1 }, { -1, -1 }, { 1, -1 } };
	public static final int[][]	delayMap	= { { 0, 2, 2 }, { 1, 2, 1 }, { 2, 2, 1 } };

	public List<Node> openList = new ArrayList<>();

	public static void main(String[] args) {
		Node start = new Node(0, 0, 0);
		Node end = new Node(2, 2, 1);
		printMap(delayMap);
		GraphNewTransferTime graphNewTransferTime = new GraphNewTransferTime();
		graphNewTransferTime.depthFirstSearch(start, end);
		System.out.println();

	}

	private void depthFirstSearch(Node start, Node end) {

		List<Node> neighborList = getNeighborList(start);

		for (Node node : neighborList) {
			boolean[][] isVisited = new boolean[delayMap[0].length][delayMap.length];
			isVisited[start.x][start.y] = true;
			depthFirstSearch(isVisited, node, end);
		}
	}

	private List<Node> getNeighborList(Node node) {
		List<Node> nodeList = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			int neighborX = node.x + directions[i][0];
			int neighborY = node.y + directions[i][1];
			if (neighborX >= 0 && neighborX < delayMap[0].length && neighborY >= 0 && neighborY < delayMap.length) {
				Node neighbor = new Node(neighborX, neighborY, delayMap[neighborX][neighborY]);
				nodeList.add(neighbor);
			}
		}
		return nodeList;
	}

	private void depthFirstSearch(boolean[][] isVisited, Node start, Node end) {
		System.out.print(getLocation(start) + "->");
		//todo:将节点设置为已经访问
		isVisited[start.x][start.y] = true;
		//todo:查找节点（x,y）的第一个紧邻节点
		Node neighbour = getFirstNeighbor(start, isVisited);
		if (neighbour.x == end.x && neighbour.y == end.y) {
			System.out.print(getLocation(neighbour));
			System.out.println();
			//isVisited = new boolean[delayMap[0].length][delayMap.length];
			//isVisited[0][0]=true;
		}

		if (neighbour.x != -1 && !isVisited[neighbour.x][neighbour.y] && !checkEnd(neighbour, end)) {
			depthFirstSearch(isVisited, neighbour, end);
		}else {
			//todo:如果邻接的节点已经访问过，找到下一个邻接节点
			getNextNeighbor(start,isVisited);
		}


	}

	private boolean checkEnd(Node neighbour, Node end) {
		if (neighbour.x == end.x && neighbour.y == end.y) { return true; }
		return false;
	}

	private Node getNextNeighbor(Node start,boolean[][] isVisited) {
		Node neighbor = new Node(-1, -1, -1);
		List<Node> neighborList = getNeighborList(start);
		for (Node node : neighborList) {
			if (!isVisited[node.x][node.y]){
				neighbor =node;
			}
		}
		return neighbor;

	}

	private Node getFirstNeighbor(Node node, boolean[][] isVisited) {
		Node neighbor = new Node(-1, -1, -1);
		for (int i = 0; i < 4; i++) {
			int neighborX = node.x + directions[i][0];
			int neighborY = node.y + directions[i][1];
			if (neighborX >= 0 && neighborX < delayMap[0].length && neighborY >= 0 && neighborY < delayMap.length && !isVisited[neighborX][neighborY]) {
				neighbor = new Node(neighborX, neighborY, delayMap[neighborX][neighborY]);
			}
		}
		return neighbor;
	}

	private String getLocation(Node start) {
		return "(" + start.x + "," + start.y + ")";
	}

	/**
	 * 打印地图
	 * @param maps
	 */
	private static void printMap(int[][] maps) {

		for (int i = 0; i < maps[0].length; i++) {
			System.out.print("\t" + i + " ");
		}
		System.out.print("\n--------------\n");
		int count = 0;
		for (int i = 0; i < maps.length; i++) {
			for (int j = 0; j < maps[0].length; j++) {
				if (j == 0) System.out.print(count++ + "|\t");
				System.out.print(maps[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
}

class Node {
	public int x;

	public int y;

	public int delay;

	public Node(int x, int y, int delay) {
		this.x = x;
		this.y = y;
		this.delay = delay;
	}

	public Node parent;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}
