package com.carter.arithmetic;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class DstarAlgorithm {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// create and initialize the map
		DstarMap map = new DstarMap(7, 7);
		// load a map
		map.loadMap("src/LeetCode/resources/map2.txt");
		// instantiate the DstarPathFinder object
		DstarPathFinder pathFinder = new DstarPathFinder(map);
		// start traversing
		pathFinder.traverseMap();
	}
}

/**
 * D*算法是一种启发式的路径搜索算法，适合面对周围环境或者周围环境存在动态变化的场景
 * D*算法通过维护一个优先队列openList来对场景中的路径节点进行搜索，所不同的是，D*不是由
 * 起始点开始搜索，而是以目标点为起始，通过将目标点置于openList中来开始搜索，直到机器人
 * 当前位置节点由队列中出队列为止（当然如果中间某节点状态有动态改变，需要重新寻路，所以
 * 才是一个动态寻路算法）
 *
 * state:路径点
 * BackPointer:指向前一个state的指针（路径搜索完毕后，通过机器人所在的state，通过BackPointer
 * 即可一步步移动到目标GoalState，b(X)=Y表示X的父辈为Y）
 * tag:表示当前路径点state的状态，有new，open，closed三种状态，new表示该路径点从未被置于openList中，
 * open表示该state正位于openList中，closed表示已经不再位于openList中
 * H(x):代价函数估计，表示当前state到goal的成本估计
 * K(x):该值是优先队列openList中的排序依据，K值最小的state位于队列头，对于处于openList上的state X，
 * K(x)表示从X被置于openList后，X到Goal的最小代价H(x),可以简单理解为K(x)将位于openList的state X划分
 * 为两种不同的状态，一种状态为Raise（K(x)<H(x)）,用来传递路径开销的增加（例如某两点之间的开销的增加，
 * 会导致相关的节点到目标的路径开销随之增加），另一种状态为Lower（如果K(x)<H(x)）,用来传递开销的路径减少
 * （例如某两点之间的开销减少，或者某一新的节点被加入到openList中，可能导致与之相关的节点到目标的路径开销
 * 随之减少）
 *
 * minimumK:表示所有位于openList上的state的最小值
 * C(X,Y):表示X与Y的路径开销
 * openList：是依据K值由小到大进行排序的优先队列
 */
class DstarMap {
	// number of columns and rows for this map
	private int	rows	= 0;
	private int	columns	= 0;

	// provides start, goal, and robotLocation states
	private DstarNode	start;
	private DstarNode	goal;
	private DstarNode	robotLocation;

	// map is implemented and a 2D array of DstarNodes
	private DstarNode[][] map;

	/**
	 * 通过给定的行数和列数创建一张地图
	 * @param rows number of rows
	 * @param columns number of columns
	 */
	public DstarMap(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;

		map = new DstarNode[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	/**
	 * 获取节点在地图中的位置
	 * @param row
	 * @param column
	 * @return
	 */
	public DstarNode getNode(int row, int column) {
		return map[row][column];
	}

	/**
	 * 得到地图中的起点
	 * @return
	 */
	public DstarNode getStart() {
		return start;
	}

	public void setStart(int x, int y) {
		this.start = getNode(x, y);
	}

	public DstarNode getGoal() {
		return goal;
	}

	/**
	 * @deprecated
	 * 设置目标节点，目标节点进行初始化
	 * @param x
	 * @param y
	 */
	public void setGoal(int x, int y) {
		this.goal = getNode(x, y);
		this.goal.setFunctionK(0);
		this.goal.setFunctionH(0);
	}

	/**
	 * Returns the local know node where the robot is located
	 *
	 * @return the node where the robot is located
	 */
	public DstarNode getRobotLocation() {
		return robotLocation;
	}

	/**
	 * Sets the robots positions
	 *
	 * @param robotLocation the node where the robot is located
	 *
	 */
	public void setRobotLocation(DstarNode robotLocation) {
		this.robotLocation = robotLocation;
	}

	/**
	 * Returns list of neighbors for a node at a given position
	 *
	 * @param row row position of node
	 * @param col column position of node
	 *
	 * @return list of neighbor nodes
	 */
	public ArrayList<DstarNode> getNeighbors(int row, int col) {
		// set top and bottom neighbor row values
		int top = row - 1;
		int bottom = row + 1;

		ArrayList<DstarNode> neighborList = new ArrayList<DstarNode>();

		// set left and right neighbor column values
		int left = col - 1;
		int right = col + 1;

		// top left adjecent neighbor
		if (!neighborOutOfBounds(top, col)) {
			neighborList.add(getNode(top, col));

		}

		// direct top neighbor
		if (!neighborOutOfBounds(bottom, col)) {
			neighborList.add(getNode(bottom, col));

		}

		// top right neighbor
		if (!neighborOutOfBounds(row, right)) {
			neighborList.add(getNode(row, right));

		}

		// left direct neighbor
		if (!neighborOutOfBounds(row, left)) {
			neighborList.add(getNode(row, left));

		}

		// right direct neighbor
		if (!neighborOutOfBounds(top, right)) {
			neighborList.add(getNode(top, right));

		}

		// bottom left neighbor
		if (!neighborOutOfBounds(bottom, left)) {
			neighborList.add(getNode(bottom, left));
		}

		// direct bottom neighbor
		if (!neighborOutOfBounds(bottom, right)) {
			neighborList.add(getNode(bottom, right));

		}

		// bottom right neighbor
		if (!neighborOutOfBounds(top, left)) {
			neighborList.add(getNode(top, left));
		}

		return neighborList;
	}

	/**
	 * 检查给定的行列是否超出地图的边界
	 * @param row row number to check
	 * @param column column number to check
	 * @return true if either row or column is out of bounds, false if the row and column are valid
	 */
	private boolean neighborOutOfBounds(int row, int column) {

		boolean outOfBounds = false;
		if (row < 0 || row >= getRows() || column < 0 || column >= getColumns()) {
			outOfBounds = true;
		}
		return outOfBounds;
	}

	/**
	 * 读取地图
	 */
	public void loadMap(String fileName) {
		int row = 0;
		int column = 0;

		File file = new File(fileName);

		if (file.exists()) {
			if (file.isFile() && file.canRead()) {
				try {
					FileInputStream in = new FileInputStream(fileName);
					int read;
					while ((read = in.read()) != -1) {
						char c = (char) read;
						if (c == 'O' || c == 'B' || c == 'U' || c == 'S' || c == 'G') {
							DstarNode node = new DstarNode(row, column);
							node.setTag("NEW");

							char state = c;
							if (c == 'S') {
								state = 'O';
								start = node;
							} else if (c == 'G') {
								state = 'O';
								goal = node;
							}

							node.setState(String.valueOf(state));
							map[row][column] = node;
							column++;
						} else {
							row++;
							column = 0;
						}
					}
					in.close();
					robotLocation = start;
				} catch (Exception e) {

				}
			} else {
				System.err.println(file.getName() + " cannot be read");
				System.exit(0);
			}
		} else {
			System.err.println("File Not Found...");
			System.exit(0);
		}

	}

	/**
	 * Prints the maps current state
	 */
	public void print() {

		int cellColumnCount = 15;
		int rowColumnCount = cellColumnCount * this.columns;

		char space = ' ';
		char rowSeparatorChar = '-';

		String rowSeparator = repeatChar(rowSeparatorChar, rowColumnCount + 1);
		String cr1; // cell row 1
		String cr2; // cell row 2
		String cr3; // cell row 3
		String cr4; // cell row 4
		String cr5; // cell row 5
		String cr6; // cell row 6

		// loop through rows
		for (int i = 0; i < map.length; i++) {
			// new row, reinitialize cell row strings
			cr1 = "";
			cr2 = "";
			cr3 = "";
			cr4 = "";
			cr5 = "";
			cr6 = "";

			// loop through cells and concatenate values to cell row strings
			for (int j = 0; j < map[i].length; j++) {
				DstarNode node = getNode(i, j);

				String startOrGoal = " ";
				if (node == start) {
					startOrGoal = "S";
				} else if (node == goal) {
					startOrGoal = "G";
				}

				String robot = "   ";
				if (node == robotLocation) {
					robot = "(*)";
				}

				String tag = node.getTag();
				String state = node.getState();
				String label = node.getLabel();
				String hval = "h:" + String.format("% ,.1f", node.getFunctionH());
				String kval = "k:" + String.format("% ,.1f", node.getFunctionK());

				String bp = "";
				if (node.getBackPointer() == null) {
					bp = "b: " + repeatChar(space, 11);
				} else {
					bp = "b: " + node.getBackPointer().getLabel() + repeatChar(space, 9);
				}

				cr1 += "|" + tag + repeatChar(space, 14 - tag.length() - state.length()) + state;
				cr2 += "|" + label + repeatChar(space, 14 - label.length());
				cr3 += "|" + hval + repeatChar(space, 14 - hval.length());
				cr4 += "|" + kval + repeatChar(space, 14 - kval.length());
				cr5 += "|" + bp;

				cr6 += "|" + repeatChar(space, 5) + robot + repeatChar(space, 5) + startOrGoal;

			}
			// close the row lines with |
			cr1 += "|";
			cr2 += "|";
			cr3 += "|";
			cr4 += "|";
			cr5 += "|";
			cr6 += "|";

			System.out.println(rowSeparator);
			System.out.println(cr1);
			System.out.println(cr2);
			System.out.println(cr3);
			System.out.println(cr4);
			System.out.println(cr5);
			System.out.println(cr6);
			;
		}

		System.out.println(rowSeparator);
	}

	private String repeatChar(char c, int num) {
		String repeated = "";
		if (num > 0) {
			repeated = new String(new char[num]).replace('\0', c);
		}

		return repeated;
	}
}

class DstarNode implements Comparable<DstarNode> {
	// Node Tag
	private String	tag		= "NEW";
	private String	state	= "O";
	private String	label	= "";

	private int	row;
	private int	column;

	private double	functionH;	// current cost to goal from this node
	private double	functionK;	// lowest value of h that this node has seen
	//private ArrayList<DstarNode> neighborList;

	private DstarNode backPointer;

	public DstarNode(int row, int column) {

		// let this column know where it is at
		this.row = row;
		this.column = column;

		// concatenate row and column to generate the label
		// label will be used as a key in the cost hashtable
		this.setLabel(Integer.toString(row) + Integer.toString(column));

		this.functionH = 0;
		this.functionK = 0;
		setBackPointer(null);
		//neighborList = new ArrayList<DstarNode>();
	}

	public DstarNode getBackPointer() {
		return backPointer;
	}

	public void setBackPointer(DstarNode backPointer) {
		this.backPointer = backPointer;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public double getFunctionH() {
		return functionH;
	}

	public void setFunctionH(double functionH) {
		this.functionH = functionH;
	}

	public double getFunctionK() {
		return functionK;
	}

	public void setFunctionK(double functionK) {
		this.functionK = functionK;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Parameters:
	 * o - the object to be compared.
	 * R:
	 * a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	 */
	public int compareTo(DstarNode node) {

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

class CostTable {
	// cost table is stored using a hash table
	private HashMap<String, Double> table;

	/**
	 * Default constructor
	 */
	public CostTable() {
		table = new HashMap<String, Double>();
	}

	/**
	 * Uses the labels from 2 nodes to build a hash key and stores value
	 * at the hash key.
	 *
	 * Costs are 2 way, so we only need to store a combination of the key/value once
	 *
	 * @param node1 label of node for part of hash key
	 * @param node2 lable of node for part of hash key
	 * @param value value to store key
	 */
	public void setValue(String node1, String node2, double value) {
		String hash1 = node1 + node2;

		if (!isSet(node1, node2)) {
			table.put(hash1, value);
		}
	}

	/**
	 * Updates the value for the derived hash key.  Since costs are 2 way the value
	 * only needs to be stored once, so the key can be node1+node2 or node2+node1
	 *
	 * @param node1 label of node for part of hash key
	 * @param node2 lable of node for part of hash key
	 * @param value value to store for key
	 */
	public void updateValue(String node1, String node2, double value) {
		String hash1 = node1 + node2;
		String hash2 = node2 + node1;

		if (table.containsKey(hash1)) { // if hash1 exits, then update hash1
			table.put(hash1, value);
		} else if (table.containsKey(hash2)) { // else if hash2 exists, then update hash2
			table.put(hash2, value);
		}
	}

	/**
	 * Checks to see of the table contains the derived key
	 *
	 * @param node1 label of node for part of hash key
	 * @param node2 lable of node for part of hash key
	 * @return true if a key exists for node1+node2 or node2+node1 otherwise false
	 */
	private boolean isSet(String node1, String node2) {
		String hash1 = node1 + node2;
		String hash2 = node2 + node1;

		boolean exists = false;
		if (table.containsKey(hash1)) {
			exists = true;
		} else if (table.containsKey(hash2)) {
			exists = true;
		}

		return exists;
	}

	/**
	 * Returns the value at the derived hash key
	 *
	 * @param node1 label of node for part of hash key
	 * @param node2 lable of node for part of hash key
	 * @return  value at the given hash key, -1 if it isn't set
	 */
	public double getValue(String node1, String node2) {
		String hash1 = node1 + node2;
		String hash2 = node2 + node1;

		double value = -1;
		boolean set = isSet(node1, node2);

		if (set) {
			if (table.containsKey(hash1)) {
				value = table.get(hash1);
			} else if (table.containsKey(hash2)) {
				value = table.get(hash2);
			}
		}

		return value;
	}

	/**
	 * Prints the cost table in no particular order
	 */
	public void printCostTable() {
		int count = 0;
		for (String key : table.keySet()) {

			double value = table.get(key);
			System.out.println(count + ": " + key + "=>" + String.format("% ,.1f", value));
			count++;
		}
	}
}

class DstarPathFinder {
	// Define constant values for path costs
	public static final double	NORMAL_COST		= 1.0;
	public static final double	ADJACENT_COST	= 1.4;
	public static final double	INFINITY_COST	= 10000;

	// define cost table object
	private CostTable costs;
	// define map object
	private DstarMap map;
	// define open list data structure
	private PriorityQueue<DstarNode> openList;

	// provide DstarNode objects for local storage
	// of start, goal, and currentRobotPosition states
	private DstarNode	start;
	private DstarNode	goal;
	private DstarNode	currentRobotPosition;

	/**
	 * Default constructor
	 *
	 * @param map a valid DstarMap
	 */
	public DstarPathFinder(DstarMap map) {
		costs = new CostTable();
		openList = new PriorityQueue<DstarNode>();
		this.map = map;
		start = map.getStart();
		currentRobotPosition = map.getRobotLocation();
		goal = map.getGoal();

		buildCostTable();
	}

	/**
	 * todo：调用这个方法找到最优路径
	 */
	public void traverseMap() {

		//todo：minimumK由processState()方法计算得到
		double minimumK;

		//todo:将目标点放入到openList中
		insert(goal, 0);
		map.print();

		// print the map as initialized and prompt user to start next seciont
		int userInput;
		try {
			System.out.println("Map has been initialized!");
			System.out.println("Ready to begin (press enter):");
			userInput = System.in.read();
		} catch (Exception e) {

		}

		//todo:循环处理节点状态直到minimumK == -1或者开始节点的状态变为closed
		do {
			minimumK = processState();
		} while (minimumK != -1.0 && !start.getTag().equalsIgnoreCase("CLOSED"));

		// after processing initial loop of process state
		// print the current state of the map
		try {
			map.print();
			System.out.println("Map has been expanded from goal...");
			System.out.println("We will either begin tracing to goal, or the goal is unreachable (press enter):");
			userInput = System.in.read();
		} catch (Exception e) {

		}

		// if minimum_K == -1.0 then goal is unreachable
		if (minimumK == -1.0) {
			System.out.println("Goal is unreachable...");
			System.exit(0);
		} else {

			// start tracing path through back pointers
			// trace until the goal is reached or the next node is unknown
			do {
				DstarNode next;
				DstarNode here;
				boolean unknownFound = false;
				map.print();

				do {
					// start from where to robot is current located
					here = map.getRobotLocation();
					// get next node from current location back pointer
					next = here.getBackPointer();

					// is next state unknown?
					unknownFound = next.getState().equals("U");

					// next state non unknown?
					if (!unknownFound) {

						// move the robot
						map.setRobotLocation(next);
						currentRobotPosition = map.getRobotLocation();

						// print the map, wait for user input
						map.print();
						try {
							System.out.println("Robot Moved!!");
							System.out.println("Move again (press enter):");
							userInput = System.in.read();
						} catch (Exception e) {

						}
					}
				} while (currentRobotPosition != goal && !unknownFound);

				// if we reach the goal, exit
				if (currentRobotPosition == goal) {
					System.out.println("Goal has been reached!");
					System.exit(0);
				} else {
					// we are not at the goal yet
					// let the user know we found an unknown obstacle
					// prompt for input to continue
					try {
						System.out.println("Unknown Obstacle!!!");
						System.out.println("Find another way (press enter):");
						userInput = System.in.read();
					} catch (Exception e) {

					}

					// modify the cost from here to next to infinity
					modifyCost(here, next, INFINITY_COST);

					// loop process state again until
					// minimumK < h(next) or minimumK == -1.0
					do {
						minimumK = processState();
					} while (minimumK < next.getFunctionH() && minimumK != -1.0 && !currentRobotPosition.getTag().equalsIgnoreCase("CLOSED"));//*BUG FIX, we weren't stopping the process state when the current robot position was closed, this caused the bot to recalculate too much and overwrite the correct path backpointers*//&& !(currentRobotPosition.equals(openList.peek())));

					// if mimimum_K == -1.0 goal is unreachable, exit application
					if (minimumK == -1.0) {
						System.out.println("Goal is unreachable!");
						System.exit(0);
					}

					// print the map, wait for user input
					map.print();
					try {
						System.out.println("Whew... I found my way!!");
						System.out.println("Move again (press enter):");
						userInput = System.in.read();
					} catch (Exception e) {

					}
				}

			} while (true);
		}
	}

	/**
	 * Modifies the cost of the paths of the unknown neighbors to infinity
	 *
	 * @param current
	 * @param neighbor
	 * @param newVal
	 * @return
	 */
	private double modifyCost(DstarNode current, DstarNode neighbor, double newVal) {

		//costs.updateValue(current.getLabel(), neighbor.getLabel(), newVal);

		//discrepency.setFunctionH(INFINITY_COST);
		ArrayList<DstarNode> neighborList = map.getNeighbors(neighbor.getRow(), neighbor.getColumn());

		// update the arc path costs of neighbor to its neighbors
		for (DstarNode each : neighborList) {
			costs.updateValue(neighbor.getLabel(), each.getLabel(), INFINITY_COST);
		}
		//neighbor.setFunctionH(newVal);
		// if neight is closed, then add it back to the open list with new h value
		if (neighbor.getTag().equalsIgnoreCase("CLOSED")) {
			insert(neighbor, newVal);
		}

		// get the new minimum k value on the open list
		DstarNode currentMin = openList.peek();

		return currentMin.getFunctionK();
	}

	/**
	 * todo：处理当前节点的状态并设置邻点
	 * @return
	 */
	private double processState() {
		double oldFunctionK;
		ArrayList<DstarNode> neighbors;
		int userInput;
		// STEP 1
		// if open list is empty exit return -1
		if (openList.isEmpty()) { return -1.0; }
		if (currentRobotPosition.getFunctionK() == 10000 && currentRobotPosition.getFunctionH() == 10000) {
			return -1.0;
		}

		// get node from head of openList (with mink)
		DstarNode currentNode = openList.remove();

		oldFunctionK = currentNode.getFunctionK();//oldFunctionK = Get Min K

		// and set it to closed
		currentNode.setTag("CLOSED");//Delete X

		// STEP 2 Re-routing if necessary
		// refers to L4 - L7 in paper by Stentz
		if (oldFunctionK < currentNode.getFunctionH()) {

			System.out.println("Step 2 - Rerouting - node: " + currentNode.getLabel());

			neighbors = map.getNeighbors(currentNode.getRow(), currentNode.getColumn());
			for (DstarNode neighbor : neighbors) {
				System.out.println("Updating Neighbor: " + neighbor.getLabel());
				double costXThroughY = neighbor.getFunctionH() + costs.getValue(neighbor.getLabel(), currentNode.getLabel());

				if (neighbor.getFunctionH() <= oldFunctionK && currentNode.getFunctionH() > costXThroughY) {
					System.out.println("-- step 2 -- set back pointer to " + neighbor.getLabel());
					System.out.println("current: " + currentNode.getLabel());

					currentNode.setBackPointer(neighbor);
					currentNode.setFunctionH(costXThroughY);
				}
			}
		}

		// Step 3 - usually done in initial map state processing
		// refers to L8 - L13 in paper by Stentz
		if (oldFunctionK == currentNode.getFunctionH()) {
			//System.out.println("Start Step 3");

			neighbors = map.getNeighbors(currentNode.getRow(), currentNode.getColumn());

			for (DstarNode neighbor : neighbors) {
				double costThroughX = currentNode.getFunctionH() + costs.getValue(currentNode.getLabel(), neighbor.getLabel());
				double roundedNumber = (double) Math.round(costThroughX * 10) / 10;
				if (neighbor.getTag().equalsIgnoreCase("NEW") || (neighbor.getBackPointer() == currentNode && neighbor.getFunctionH() != costThroughX) || (neighbor.getBackPointer() != currentNode && neighbor.getFunctionH() > costThroughX)) {

					neighbor.setBackPointer(currentNode);
					insert(neighbor, roundedNumber);
				}
			}
		}

		// Step 4
		// referces to L14 - L25 in paper by Stentz
		else {
			neighbors = map.getNeighbors(currentNode.getRow(), currentNode.getColumn());
			for (DstarNode neighbor : neighbors) {
				double costThroughX = currentNode.getFunctionH() + costs.getValue(currentNode.getLabel(), neighbor.getLabel());
				double costXthroughY = neighbor.getFunctionH() + costs.getValue(neighbor.getLabel(), currentNode.getLabel());
				//double roundedCostThroughX = costThroughX;
				//	double roundedCostXThroughY = costXthroughY;

				double roundedCostThroughX = (double) Math.round(costThroughX * 10) / 10;
				double roundedCostXThroughY = (double) Math.round(costXthroughY * 10) / 10;
				double roundedH;

				// Step 5
				// refers to L16 - L18 paper by Stentz
				if (neighbor.getTag().equalsIgnoreCase("NEW") || (neighbor.getBackPointer() == currentNode && neighbor.getFunctionH() != costThroughX)) {

					//routeFirstViaSecond(neighbor,currentNode);
					neighbor.setBackPointer(currentNode);
					insert(neighbor, roundedCostThroughX);

				} else if (neighbor.getBackPointer() != currentNode && neighbor.getFunctionH() > roundedCostThroughX) {
					roundedH = (double) Math.round(currentNode.getFunctionH() * 10) / 10;
					insert(currentNode, roundedH);

				} else if (neighbor.getBackPointer() != currentNode && currentNode.getFunctionH() > roundedCostXThroughY && neighbor.getTag().equalsIgnoreCase("CLOSED") && neighbor.getFunctionH() > oldFunctionK) {
					roundedH = (double) Math.round(neighbor.getFunctionH() * 10) / 10;
					insert(neighbor, roundedH);
				}
			}
		}

		// set minK, of openList is not empty, then get the minK from open list
		double minK = -1.0;
		if (!openList.isEmpty()) {
			minK = openList.peek().getFunctionK();
		}
		try {

			System.out.println("Planning....");
			System.out.println("Current Path:");
			map.print();
			System.out.println("Press ENTER to continue...");
			userInput = System.in.read();
		} catch (Exception e) {

		}

		return minK;

	}

	/**
	 * routeFristViaSecond from Dr. Kays notes, however we don't use this
	 *
	 * TODO remove
	 *
	 * @param Y
	 * @param X
	 */
	private void routeFirstViaSecond(DstarNode Y, DstarNode X) {
		Y.setBackPointer(X);
		Y.setFunctionH(X.getFunctionH() + costs.getValue(X.getLabel(), Y.getLabel()));
		if (Y.getTag().equalsIgnoreCase("NEW")) {
			Y.setFunctionK(Y.getFunctionH());
		}

		/*
		// since we are using a priority queue
		// if Y is on the openList and the K
		if(Y.getTag().equals("OPEN")){
			openList.remove(Y);
		}
		*/
		Y.setTag("OPEN");
		openList.add(Y);
	}

	/**
	 * Update a nodes k and h value and inserts/reinserts a node into the open list
	 *
	 * @param someNode
	 * @param newH
	 */
	private void insert(DstarNode someNode, double newH) {
		if (newH > 10000) {
			newH = 10000;
		}
		double roundedH;
		double currentK;
		DstarNode temp;
		if (someNode.getTag().equalsIgnoreCase("NEW")) {
			someNode.setFunctionK(newH);
			someNode.setFunctionH(newH);
			someNode.setTag("OPEN");
			openList.add(someNode);
		}
		if (someNode.getTag().equalsIgnoreCase("OPEN")) {
			currentK = someNode.getFunctionK();

			openList.remove(someNode);

			someNode.setFunctionK(Math.min(currentK, newH));
			openList.add(someNode);

		}
		if (someNode.getTag().equalsIgnoreCase("CLOSED")) {

			int userInput;
			roundedH = (double) Math.round(someNode.getFunctionH() * 10) / 10;
			someNode.setFunctionK(Math.min(roundedH, newH));
			someNode.setFunctionH(newH);
			someNode.setTag("OPEN");
			openList.add(someNode);

		}
	}

	/**
	 * Initialized the cost table
	 *
	 * TODO a lot of this is repative, and should be fixed
	 * neightbor OutOfBounds should be a public method in DstarMap
	 */
	private void buildCostTable() {
		//loop all nodes
		// set cost between open nodes to 1 or 1.4

		int current_x = 0;
		int current_y = 0;

		DstarNode neighbor;
		for (int i = 0; i < map.getRows(); i++) {
			for (int j = 0; j < map.getColumns(); j++) {
				DstarNode current = map.getNode(i, j);

				// set top and bottom neighbor row values
				int top = i - 1;
				int bottom = i + 1;

				// set left and right neighbor column values
				int left = j - 1;
				int right = j + 1;

				// top left adjecent neighbor
				if (!neighborOutOfBounds(top, left)) {

					neighbor = map.getNode(top, left);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
					}

				}

				// direct top neighbor
				if (!neighborOutOfBounds(top, j)) {
					neighbor = map.getNode(top, j);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
					}
				}

				// top right neighbor
				if (!neighborOutOfBounds(top, right)) {
					neighbor = map.getNode(top, right);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
					}
				}

				// left direct neighbor
				if (!neighborOutOfBounds(i, left)) {
					neighbor = map.getNode(i, left);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
					}
				}

				// right direct neighbor
				if (!neighborOutOfBounds(i, right)) {
					neighbor = map.getNode(i, right);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
					}
				}

				// bottom left neighbor
				if (!neighborOutOfBounds(bottom, left)) {
					neighbor = map.getNode(bottom, left);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
					}
				}

				// direct bottom neighbor
				if (!neighborOutOfBounds(bottom, j)) {
					neighbor = map.getNode(bottom, j);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
					}
				}

				// bottom right neighbor
				if (!neighborOutOfBounds(bottom, right)) {
					neighbor = map.getNode(bottom, right);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
					}
				}
			}
		}
	}

	public void printCosts() {
		costs.printCostTable();
	}

	public void printOpenList() {

		DstarNode[] list = new DstarNode[openList.size()];

		list = openList.toArray(list);

		Arrays.sort(list);

		System.out.println("Open List Size: " + openList.size());
		System.out.println("List Head\t\tK\t\tH");
		System.out.println("------------------------------------------------");
		DstarNode node = openList.peek();
		System.out.println(node.getLabel() + "\t\t\t" + String.format("% ,.1f", node.getFunctionK()) + "\t\t" + String.format("% ,.1f", node.getFunctionH()));

		System.out.println("List Head\t\tK\t\tH");
		System.out.println("------------------------------------------------");
		for (DstarNode n : list) {
			System.out.println(n.getLabel() + "\t\t\t" + String.format("% ,.1f", n.getFunctionK()) + "\t\t" + String.format("% ,.1f", n.getFunctionH()));
		}
	}

	/**
	 * 检验紧邻节点是否超出地图界限
	 *
	 * TODO get rid of this, and make the same method in DstarMap a public method
	 *
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean neighborOutOfBounds(int row, int column) {
		boolean outOfBounds = false;
		if (row < 0 || row >= map.getRows() || column < 0 || column >= map.getColumns()) {
			outOfBounds = true;
		}
		return outOfBounds;
	}
}