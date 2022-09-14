package com.carter.arithmetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 重新用java写一遍D_star算法
 */
public class DstarCarterAlgorithm {
	public static void main(String[] args) {

	}

	//todo:初始化一张地图
	DstarCarterMap map = new DstarCarterMap(7, 7);
}

class DstarCarterMap {
	private int rows;

	private int columns;

	private DstarCarterNode[][] map;

	public DstarCarterMap(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		map = new DstarCarterNode[rows][columns];
	}

	public  void printDstarCarterMap(DstarCarterMap map){
		for (int i = 0; i < map.rows; i++) {
			String temp = "";
			for (int j = 0; j < map.columns; j++) {
				temp+=map.map[i][j].getState();
			}
			System.out.println(temp);
		}
	}

	public List<DstarCarterNode> getNeighbours(DstarCarterMap map,DstarCarterNode dstarNode){
		List<DstarCarterNode> retList = new ArrayList<>();
		int[] dstarArr = {-1, 0, 1};
		for (int i : dstarArr) {
			for (int j : dstarArr) {
				if (i==0&&j==0){
					continue;
				}
				if (dstarNode.getX()+i<0||dstarNode.getX()+i>= map.rows){
					continue;
				}
				if (dstarNode.getY()+j<0||dstarNode.getY()+j>= map.columns){
					continue;
				}
				retList.add(map.map[dstarNode.getX()+i][dstarNode.getY()+j]);
			}
		}
		return retList;
	}

	//todo:在地图中设置障碍物
	public void setObstacle(DstarCarterMap map,List<DstarCarterNode> obstacleList){
		for (DstarCarterNode obstacle : obstacleList) {
			if (obstacle.getX()<0||obstacle.getX()>=map.rows||obstacle.getY()<0||obstacle.getY()>=map.columns){
				continue;
			}
			map.map[obstacle.getX()][obstacle.getY()].setState("#");
		}
	}

}

class DstarCarter{
	public DstarCarterMap dstarMap;

	public List<DstarCarterNode> openList = new ArrayList<>();

	//获取openList当中functionK的最小值
	public int getMinFunctionK(List<DstarCarterNode> openList){
		if (openList==null){
			return -1;
		}

		int minFunctionK = openList.stream().min(new Comparator<DstarCarterNode>() {
			@Override
			public int compare(DstarCarterNode o1, DstarCarterNode o2) {
				return o1.getFunctionK() - o2.getFunctionK();
			}
		}).get().getFunctionK();

		return minFunctionK;
	}

	public void insertOpenList(DstarCarterNode dstarNode,int newH){
		if (dstarNode.getTag()=="new"){
			dstarNode.setFunctionK(newH);
		} else if (dstarNode.getTag()=="open"){
			dstarNode.setFunctionK(Math.min(dstarNode.getFunctionK(),newH));
		} else if (dstarNode.getTag()=="close"){
			dstarNode.setFunctionK(Math.min(dstarNode.getFunctionK(),newH));
		}
		dstarNode.setFunctionH(newH);
		dstarNode.setTag("open");
		this.openList.add(dstarNode);
	}
}

class DstarCarterNode {
	private static final  double INFINITY_COST = 10000;

	private int x;

	private int y;

	private DstarCarterNode parent;

	private String state = ".";

	private String tag = "new";

	private int functionH = 0;

	private int functionK = 0;

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

	public void setFunctionH(int functionH) {
		this.functionH = functionH;
	}

	public void setFunctionK(int functionK) {
		this.functionK = functionK;
	}

	public double getCost(DstarCarterNode start, DstarCarterNode end){
		if (state=="#"){
			return INFINITY_COST;
		}else {
			return Math.sqrt(Math.pow(start.x- end.x,2)+Math.pow(start.y- end.y,2));
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getFunctionH() {
		return functionH;
	}

	public int getFunctionK() {
		return functionK;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag == null ? null : tag.trim();
	}
}
