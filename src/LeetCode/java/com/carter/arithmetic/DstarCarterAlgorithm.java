package com.carter.arithmetic;

import java.util.*;

/**
 * 重新用java写一遍D_star算法
 */
public class DstarCarterAlgorithm {
	public static void main(String[] args) {
		DstarCarterMap dstarMap = new DstarCarterMap(20, 20);
		DstarCarterNode obstacle1 = new DstarCarterNode(4, 3);
		DstarCarterNode obstacle2 = new DstarCarterNode(4, 4);
		DstarCarterNode obstacle3 = new DstarCarterNode(4, 5);
		DstarCarterNode obstacle4 = new DstarCarterNode(4, 6);
		DstarCarterNode obstacle5 = new DstarCarterNode(5, 3);
		DstarCarterNode obstacle6 = new DstarCarterNode(6, 3);
		DstarCarterNode obstacle7 = new DstarCarterNode(7, 3);
		List<DstarCarterNode> obstacleList = Arrays.asList(obstacle1, obstacle2, obstacle3, obstacle4, obstacle5, obstacle6, obstacle7);
		dstarMap.setObstacle(dstarMap, obstacleList);
		dstarMap.printDstarCarterMap(dstarMap.map);

		DstarCarter dstarCarter = new DstarCarter();

		DstarCarterNode start = dstarMap.map[1][2];
		DstarCarterNode end = dstarMap.map[17][11];
		dstarCarter.runDstar(start, end, dstarMap);

	}
}

class DstarCarterMap {
	public int rows;

	public int columns;

	public DstarCarterNode[][] map;

	public DstarCarterMap(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		map = new DstarCarterNode[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				map[i][j] = new DstarCarterNode(i, j);
			}
		}

	}

	public void printDstarCarterMap(DstarCarterNode[][] map) {
		for (int i = 0; i < map[0].length; i++) {
			System.out.print("\t" + i + ",");
		}
		System.out.print("\n-----------------------------------------------------------------------------------\n");
		int count = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (j == 0) System.out.print(count++ + "|\t");
				System.out.print(map[i][j].state + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	public List<DstarCarterNode> getNeighbours(DstarCarterNode dstarNode) {
		List<DstarCarterNode> retList = new ArrayList<>();
		int[] dstarArr = { -1, 0, 1 };
		for (int i : dstarArr) {
			for (int j : dstarArr) {
				if (i == 0 && j == 0) {
					continue;
				}
				if (dstarNode.getX() + i < 0 || dstarNode.getX() + i >= rows) {
					continue;
				}
				if (dstarNode.getY() + j < 0 || dstarNode.getY() + j >= columns) {
					continue;
				}
				retList.add(map[dstarNode.getX() + i][dstarNode.getY() + j]);
			}
		}
		return retList;
	}

	//todo:在地图中设置障碍物
	public void setObstacle(DstarCarterMap map, List<DstarCarterNode> obstacleList) {
		for (DstarCarterNode obstacle : obstacleList) {
			if (obstacle.getX() < 0 || obstacle.getX() >= map.rows || obstacle.getY() < 0 || obstacle.getY() >= map.columns) {
				continue;
			}
			map.map[obstacle.getX()][obstacle.getY()].setState("#");
		}
	}

}

class DstarCarter {
	public DstarCarterMap dstarMap;

	public PriorityQueue<DstarCarterNode> openList;

	//获取openList当中functionK的最小值
	public double getMinFunctionK() {
		if (openList.isEmpty()) { return -1; }
		double minFunctionK = openList.stream().min(Comparator.comparing(DstarCarterNode::getFunctionK)).get().getFunctionK();
		return minFunctionK;
	}

	public void insertOpenList(DstarCarterNode dstarNode, double newH) {
		if (dstarNode.getTag() == "new") {
			dstarNode.setFunctionK(newH);
			dstarNode.setFunctionH(newH);
			dstarNode.setTag("open");
			openList.add(dstarNode);
		} else if (dstarNode.getTag() == "open") {
			openList.remove(dstarNode);
			dstarNode.setFunctionK(Math.min(dstarNode.getFunctionK(), newH));
			openList.add(dstarNode);

		} else if (dstarNode.getTag() == "close") {
			dstarNode.setFunctionK(Math.min(dstarNode.getFunctionH(), newH));
			dstarNode.setFunctionH(newH);
			dstarNode.setTag("open");
			openList.add(dstarNode);
		}

	}

	public void removeOpenList(DstarCarterNode dstarNode) {
		if (dstarNode.getTag() == "open") {
			dstarNode.setTag("close");
		}
		openList.remove(dstarNode);
	}

	public void modifyCost(DstarCarterNode dstarNode) {
		if (dstarNode.getTag() == "close") {
			insertOpenList(dstarNode, dstarNode.parent.functionH + dstarNode.getCost(dstarNode, dstarNode.parent));
		}
	}

	public DstarCarterNode getMinDstarNode() {
		DstarCarterNode minDstarNode = new DstarCarterNode();
		if (openList != null && openList.size() >= 1) {
			minDstarNode = openList.stream().min(new Comparator<DstarCarterNode>() {
				@Override
				public int compare(DstarCarterNode o1, DstarCarterNode o2) {
					return (int) (o1.getFunctionK() - o2.getFunctionK());
				}
			}).get();

		}
		return minDstarNode;
	}

	public double processDstarNode(DstarCarterNode endNode, DstarCarterMap dstarMap) {
		double oldFunctionK;
		if (openList.isEmpty()){
			return -1.0;
		}
		DstarCarterNode minDstarNode = openList.remove();
		oldFunctionK = minDstarNode.getFunctionK();
		//removeOpenList(minDstarNode);
		minDstarNode.setTag("close");

		System.out.println("-->DstarNode:" + minDstarNode.getX() + "," + minDstarNode.getY() + " " + minDstarNode.getFunctionK() + "," + minDstarNode.getFunctionH());
		if (oldFunctionK < minDstarNode.getFunctionH()) {
			for (DstarCarterNode neighbour : dstarMap.getNeighbours(minDstarNode)) {
				if (neighbour.getTag() != "new" && neighbour.getFunctionH() <= oldFunctionK && minDstarNode.getFunctionH() > neighbour.getFunctionH() + minDstarNode.getCost(minDstarNode, neighbour)) {
					minDstarNode.setParent(neighbour);
					minDstarNode.setFunctionH(neighbour.getFunctionH() + minDstarNode.getCost(minDstarNode, neighbour));
				}
			}
			System.out.println("oldFunctionK < minDstarNode.functionH:" + minDstarNode.getFunctionK() + " " + minDstarNode.getFunctionH());
		} else if (oldFunctionK == minDstarNode.getFunctionH()) {
			for (DstarCarterNode neighbour : dstarMap.getNeighbours(minDstarNode)) {
				if (neighbour.getTag() == ("new") || (neighbour.getParent() == minDstarNode && neighbour.getFunctionH() != minDstarNode.getFunctionH() + minDstarNode.getCost(minDstarNode, neighbour)) || (neighbour.getParent() != minDstarNode && neighbour.getFunctionH() > minDstarNode.getFunctionH() + minDstarNode.getCost(minDstarNode, neighbour)) && neighbour != endNode) {
					neighbour.setParent(minDstarNode);
					insertOpenList(neighbour, minDstarNode.getFunctionH() + minDstarNode.getCost(minDstarNode, neighbour));
				}
			}
			System.out.println("oldFunctionK = minDstarNode.functionH:" + minDstarNode.getFunctionK() + " " + minDstarNode.getFunctionH());
		} else {
			for (DstarCarterNode neighbour : dstarMap.getNeighbours(minDstarNode)) {
				if (neighbour.getTag() == ("new") || (neighbour.getParent() == minDstarNode && neighbour.getFunctionH() != minDstarNode.getFunctionH() + minDstarNode.getCost(minDstarNode, neighbour))) {
					neighbour.setParent(minDstarNode);
					insertOpenList(neighbour, minDstarNode.getFunctionH() + minDstarNode.getCost(minDstarNode, neighbour));
				} else {
					if (neighbour.getParent() != minDstarNode && neighbour.getFunctionH() > minDstarNode.getFunctionH() + minDstarNode.getCost(minDstarNode, neighbour)) {
						insertOpenList(minDstarNode, minDstarNode.getFunctionH());
					} else {
						if (neighbour.getParent() != minDstarNode && minDstarNode.getFunctionH() > neighbour.getFunctionH() + minDstarNode.getCost(minDstarNode, neighbour) && neighbour.getTag() == ("close") && neighbour.getFunctionH() > oldFunctionK) {
							insertOpenList(neighbour, neighbour.getFunctionH());
						}
					}
				}
			}
			System.out.println("oldFunctionK > minDstarNode.functionH:" + minDstarNode.getFunctionK() + " " + minDstarNode.getFunctionH());
		}

		return getMinFunctionK();
	}

	public void runDstar(DstarCarterNode start, DstarCarterNode end, DstarCarterMap dstarMap) {
		openList = new PriorityQueue<DstarCarterNode>();
		double minimumK = 0;
		insertOpenList(end,0);

		while (minimumK!=-1.0 &&!start.getTag().equals("close")) {
			 minimumK = processDstarNode(dstarMap.map[end.getX()][end.getY()], dstarMap);
		}
		start.setState("S");
		DstarCarterNode startNode = start;
		while (startNode != end) {
			startNode = startNode.getParent();
			startNode.setState("+");
		}
		startNode.setState("E");
		System.out.println("障碍物未发生变化时，搜索的路径如下：");
		dstarMap.printDstarCarterMap(dstarMap.map);
		DstarCarterNode temp = start;
		DstarCarterNode obstacle1 = new DstarCarterNode(9, 3);
		DstarCarterNode obstacle2 = new DstarCarterNode(9, 4);
		DstarCarterNode obstacle3 = new DstarCarterNode(9, 5);
		DstarCarterNode obstacle4 = new DstarCarterNode(9, 6);
		DstarCarterNode obstacle5 = new DstarCarterNode(9, 7);
		DstarCarterNode obstacle6 = new DstarCarterNode(9, 8);
		DstarCarterNode obstacle7 = new DstarCarterNode(8, 3);

		List<DstarCarterNode> obstacleList = Arrays.asList(obstacle1, obstacle2, obstacle3, obstacle4, obstacle5, obstacle6,obstacle7);
		dstarMap.setObstacle(dstarMap, obstacleList);
		/**
		 * 从起始点开始，往目标点行进，当遇到障碍物时，重新修改代价，再寻找路径
		 */
		while (temp != end) {
			temp.setState("*");
			if (temp.getParent().getState() == ("#")) {
				modifyNode(temp, end,dstarMap);
				continue;
			}
			temp = temp.getParent();
		}
		temp.setState("E");
		System.out.println("障碍物发生变化时，搜索的路径如下(*为更新的路径)：");
		dstarMap.printDstarCarterMap(dstarMap.map);
	}

	public void modifyNode(DstarCarterNode dstarNode, DstarCarterNode endNode,DstarCarterMap dstarMap) {
		modifyCost(dstarNode);
		while (true) {
			double minK = processDstarNode(endNode, dstarMap);
			if (minK >= dstarNode.getFunctionH()) {
				break;
			}
		}
	}

}

class DstarCarterNode implements Comparable<DstarCarterNode>{
	private static final double INFINITY_COST = 2 ^ 63 - 1;

	public int x;

	public int y;

	public DstarCarterNode parent;

	public String state = ".";

	public String tag = "new";

	public double functionH = 0;

	public double functionK = 0;

	public DstarCarterNode getParent() {
		return parent;
	}

	public void setParent(DstarCarterNode parent) {
		this.parent = parent;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public DstarCarterNode(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public DstarCarterNode() {
	}

	public void setFunctionH(double functionH) {
		this.functionH = functionH;
	}

	public void setFunctionK(double functionK) {
		this.functionK = functionK;
	}

	public double getCost(DstarCarterNode start, DstarCarterNode end) {
		if (start.getState() == ("#") || end.getState() == ("#")) {
			return INFINITY_COST;
		} else {
			return Math.sqrt(Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2));
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getFunctionH() {
		return functionH;
	}

	public double getFunctionK() {
		return functionK;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag == null ? null : tag.trim();
	}

	@Override
	public int compareTo(DstarCarterNode node) {
		int compare;

		if (this.functionK < node.functionK) {
			compare = -1;
		} else if (this.functionK > node.functionK) {
			compare = 1;
		} else {
			compare = 0;
		}

		return compare;
	}
}
