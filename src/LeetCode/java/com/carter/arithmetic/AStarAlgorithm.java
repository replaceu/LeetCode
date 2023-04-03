package com.carter.arithmetic;

/**
 * 拥有一个地图，地图上面有起点和终点，一个机器人在起点，希望用最短的距离到达终点
 * openList:用来存储当前能够达到的点，closeList：用来存储已经到达过的点
 * F(x) = G(x)+H(x)
 */
import java.util.ArrayList;
import java.util.List;

public class AStarAlgorithm {

	/**
	 * G代表的是从初始位置A沿着已生成的路径到指定待检测格子的移动开销
	 * H指定待测格子到目标节点B的估计移动开销。
	 * 父节点(parent)在路径规划中用于回溯的节点，开发时可考虑为双向链表结构中的父结点指针
	 */

	public static void main(String[] args) {
		//定点:起点终点
		Node startNode = new Node(8, 2);
		Node endNode = new Node(2, 4);
		//尝试寻找最短路径
		Node path = new AStarAlgorithm().findAstarPath(startNode, endNode);

		printMap(map);

		List<Node> arrayList = path != null ? getPaths(path) : null;

		printPaths(arrayList);

	}

	private static void printMap(int[][] maps) {

		for (int i = 0; i < maps[0].length; i++) {
			System.out.print("\t" + i + ",");
		}
		System.out.print("\n-----------------------------------------\n");
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

	//从终点开始沿着路径退回起点
	private static List<Node> getPaths(Node endNode) {
		List<Node> nodeList = new ArrayList<Node>();
		Node pre = endNode;
		while (pre != null) {
			nodeList.add(new Node(pre.x, pre.y));
			pre = pre.parent;
		}
		return nodeList;
	}

	public static void printPaths(List<Node> arrayList) {
		// 地图形式
		for (int i = 0; i < map[0].length; i++) {
			System.out.print("\t" + i + ",");
		}
		System.out.print("\n-----------------------------------------\n");
		int count = 0;

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (j == 0) System.out.print(count++ + "|\t");
				if (exists(arrayList, i, j)) {
					System.out.print("@\t");
				} else {
					System.out.print(map[i][j] + "\t");
				}

			}
			System.out.println();
		}
		System.out.println();
		// 路径形式
		for (int i = arrayList.size() - 1; i >= 0; i--) {
			if (i == 0) System.out.print(arrayList.get(i));
			else System.out.print(arrayList.get(i) + "->");
		}
		System.out.println();
	}

	private static boolean exists(List<Node> nodeList, int x, int y) {
		for (Node node : nodeList) {
			if ((node.x == x) && (node.y == y)) { return true; }
		}
		return false;
	}

	public static Node find(List<Node> maps, Node point) {
		for (Node n : maps)
			if ((n.x == point.x) && (n.y == point.y)) { return n; }
		return null;
	}

	/**
	 * todo:模拟的地图
	 */
	public static final int[][] map = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0 }, };

	/**
	 * todo:定义方向，支持斜着走
	 * (-1, 1)|(0, 1)|(1, 1)
	 * -------------------
	 * (-1, 0)|(0, 0)|(1, 0)
	 * -------------------
	 * (-1,-1)|(0,-1)|(1,-1)
	 */
	public static final int[][] direction = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

	public static final int step = 10;

	private List<Node>	openList	= new ArrayList<>();
	private List<Node>	closeList	= new ArrayList<>();

	/**
	 * 通过输入一个点的坐标(x,y)以及目标点的坐标(targetX,targetY)从而计算距离
	 */
	private int calculateH(Node end, Node node) {
		int distance = Math.abs(node.x - end.x) + Math.abs(node.y - end.y);
		return distance * distance;
	}

	private Node findAstarPath(Node start, Node end) {
		//todo:将起点加入openList
		openList.add(start);
		while (openList.size() > 0) {
			//todo:遍历openList，查找funcF最小的节点，将其作为当前要处理的节点
			Node currentNode = findMinCostNodeOpenList();
			//todo:从openList中移除
			openList.remove(currentNode);
			//todo:将这个节点移到closeList
			closeList.add(currentNode);
			List<Node> neighborNodeList = getNeighborNodeList(currentNode);
			for (Node neighbor : neighborNodeList) {
				//todo:如果当前邻节点已经在openList中
				if (exist(openList, neighbor.x, neighbor.y)) {
					foundPoint(currentNode, neighbor);
				} else {
					notFoundPoint(currentNode, end, neighbor);
				}
			}
			//todo:如果终点在openList中，则代表找到路径
			if (find(openList, end) != null) { return find(openList, end); }
		}
		return find(openList, end);
	}

	/**
	 *
	 * @param tmpStart：起始点
	 * @param end：目标点
	 * @param node：待检测点
	 */
	private void notFoundPoint(Node tmpStart, Node end, Node node) {
		node.parent = tmpStart;
		node.funcG = calculateG(tmpStart, node);
		node.funcH = calculateH(end, node);
		node.calculateF();
		openList.add(node);
	}

	private void foundPoint(Node tmpStart, Node node) {
		//todo:计算起始点沿着已经生成的路线到待检测点的开销
		int funcG = calculateG(tmpStart, node);
		//todo:如果途径当前节点tmpStart到达该节点的node的距离G更小时，更新F
		if (funcG < node.funcG) {
			node.parent = tmpStart;
			node.funcG = funcG;
			node.calculateF();
		}
	}

	private int calculateG(Node start, Node node) {
		//todo:这是个累加，将当前的的开销与之前路线的开销
		int funcG = step;
		int parentG = node.parent != null ? node.parent.funcG : 0;
		return funcG + parentG;
	}

	public Node findMinCostNodeOpenList() {
		Node tmpNode = openList.get(0);
		for (Node node : openList) {
			if (node.funcF < tmpNode.funcF) {
				tmpNode = node;
			}
		}
		return tmpNode;
	}

	//todo:找到相邻的节点列表
	public List<Node> getNeighborNodeList(Node currentNode) {
		List<Node> nodeList = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			int neighborX = currentNode.x + direction[i][0];
			int neighborY = currentNode.y + direction[i][1];
			//todo:如果当前节点的相邻节点可达且不在闭合列表当中（closeList）中
			if (canReach(neighborX, neighborY) && !exist(openList, neighborX, neighborY)) {
				nodeList.add(new Node(neighborX, neighborY));
			}
		}
		return nodeList;
	}

	private boolean exist(List<Node> openList, int x, int y) {
		for (Node node : openList) {
			if ((node.x == x) && (node.y == y)) { return true; }
		}
		return false;
	}

	private boolean canReach(int x, int y) {
		if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) { return map[x][y] == 0; }
		return false;
	}

	public static boolean exists(List<Node> maps, Node node) {
		for (Node map : maps) {
			if ((map.x == node.x) && (map.y == node.y)) { return true; }
		}
		return false;
	}
}

//todo:创建的节点，以及父节点
class Node {
	//F是H(x)+G(x)的总和
	public int funcF;
	//G是从初始位置A沿着已生成的路径到指定待检测格子的移动开销
	public int funcG;
	//H指定待测格子到目标节点B的估计移动开销
	public int funcH;
	//节点的x坐标
	public int x;
	//节点的y坐标
	public int y;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Node parent;

	public void calculateF() {
		funcF = funcG + funcH;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
