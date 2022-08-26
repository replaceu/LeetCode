package com.carter.arithmetic;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class DstarAlgorithm {

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
class DstarNode implements Comparable<DstarNode> {
	//new tag
	private String	tag		= "new";
	private String	state	= "0";
	private String	label	= "";

	private int	row;
	private int	column;

	// current cost to goal from this node
	private double h;
	// lowest value of h that this node has seen
	private double k;

	private DstarNode backPointer;

	public DstarNode(int row, int column) {
		this.row = row;
		this.column = column;
		//concatenate row and column to generate the label,label will be used as a key in the cost hashtable
		this.setLabel(Integer.toString(row) + Integer.toString(column));

		this.h = 0;
		this.k = 0;
		setBackPointer(null);
	}

	public DstarNode getBackPointer() {
		return backPointer;
	}

	public void setBackPointer(DstarNode backPointer) {
		this.backPointer = backPointer;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag == null ? null : tag.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label == null ? null : label.trim();
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	@Override
	public int compareTo(DstarNode o) {
		return 0;
	}
}

class CostTable {
	private Map<String, Double> table;

	public CostTable() {
		this.table = new HashMap<String, Double>();
	}

	public void setValue(String node1, String node2, double value) {
		String hashNode = node1 + node2;
		if (!isSet(node1, node2)) {
			table.put(hashNode, value);
		}
	}

	public void updateValue(String node1, String node2, double value) {
		String hashFirst = node1 + node2;
		String hashSecond = node2 + node1;
		if (table.containsKey(hashFirst)) {
			table.put(hashFirst, value);
		} else if (table.containsKey(hashSecond)) {
			table.put(hashSecond, value);
		}
	}

	public double getValue(String node1, String node2) {
		String hashFirst = node1 + node2;
		String hashSecond = node2 + node1;

		double value = -1;
		boolean set = isSet(node1, node2);
		if (set) {
			if (table.containsKey(hashFirst)) {
				value = table.get(hashFirst);
			} else if (table.containsKey(hashSecond)) {
				value = table.get(hashSecond);
			}
		}
		return value;
	}

	public void printCostTable() {
		int count = 0;
		for (String key : table.keySet()) {

			double value = table.get(key);
			System.out.println(count + ": " + key + "=>" + String.format("% ,.1f", value));
			count++;
		}
	}

	public boolean isSet(String node1, String node2) {
		String hashFirst = node1 + node2;
		String hashSecond = node2 + node1;

		boolean exists = false;
		if (table.containsKey(hashFirst) || table.containsKey(hashSecond)) {
			exists = true;
		}

		return exists;
	}
}

class DstarMap {

	// number of columns and rows for this map
	private int	rows	= 0;
	private int	columns	= 0;

	// provides start, goal, and robotLocation states
	private DstarNode	start;
	private DstarNode	goal;
	private DstarNode	robotLocation;

	//map is implemented and a 2D array of DstarNodes
	private DstarNode[][] dstarMap;

	public DstarMap(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		dstarMap = new DstarNode[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public DstarNode getNode(int row, int column) {
		return dstarMap[row][column];
	}

	public DstarNode getStart() {
		return start;
	}

	public void setStart(int x, int y) {
		this.start = getNode(x, y);
	}

	public DstarNode getGoal() {
		return goal;
	}

	public void setGoal(int x, int y) {
		this.goal = getNode(x, y);
		this.goal.setK(0);
		this.goal.setH(0);
	}

	public DstarNode getRobotLocation() {
		return robotLocation;
	}

	public void setRobotLocation(DstarNode robotLocation) {
		this.robotLocation = robotLocation;
	}

	public List<DstarNode> getNeighbors(int row, int col) {
		int top = row - 1;
		int bottom = row + 1;
		int left = col - 1;
		int right = col + 1;

		List<DstarNode> neighborList = new ArrayList<>();
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

	private boolean neighborOutOfBounds(int row, int column) {
		boolean outOfBonds = false;
		if (row < 0 || row >= getRows() || column < 0 || column >= getColumns()) {
			outOfBonds = true;
		}
		return outOfBonds;
	}

	public void loadMap(String filename) {
		int row = 0;
		int column = 0;

		File file = new File(filename);

		if (file.exists()) {
			if (file.isFile() && file.canRead()) {
				try {
					FileInputStream in = new FileInputStream(filename);
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
							dstarMap[row][column] = node;
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

		int cell_column_count = 15;
		int row_column_count = cell_column_count * this.columns;

		char space = ' ';
		char row_seperator_char = '-';

		String row_seperator = repeatChar(row_seperator_char, row_column_count + 1);
		String cr1; // cell row 1
		String cr2; // cell row 2
		String cr3; // cell row 3
		String cr4; // cell row 4
		String cr5; // cell row 5
		String cr6; // cell row 6

		// loop through rows
		for (int i = 0; i < dstarMap.length; i++) {
			// new row, reinitialize cell row strings
			cr1 = "";
			cr2 = "";
			cr3 = "";
			cr4 = "";
			cr5 = "";
			cr6 = "";

			// loop through cells and concatenate values to cell row strings
			for (int j = 0; j < dstarMap[i].length; j++) {
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
				String hval = "h:" + String.format("% ,.1f", node.getH());
				String kval = "k:" + String.format("% ,.1f", node.getK());

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

			System.out.println(row_seperator);
			System.out.println(cr1);
			System.out.println(cr2);
			System.out.println(cr3);
			System.out.println(cr4);
			System.out.println(cr5);
			System.out.println(cr6);
			;
		}

		System.out.println(row_seperator);
	}

	private String repeatChar(char c, int num) {
		String repeated = "";
		if (num > 0) {
			repeated = new String(new char[num]).replace('\0', c);
		}

		return repeated;
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
	private DstarMap dstarMap;
	// define open list data structure
	private PriorityQueue<DstarNode> openList;

	// provide DstarNode objects for local storage
	// of start, goal, and currentRobotPosition states
	private DstarNode	start;
	private DstarNode	goal;
	private DstarNode	currentRobotPosition;

	public DstarPathFinder(DstarMap dstarMap) {
		this.dstarMap = dstarMap;
		costs = new CostTable();
		openList = new PriorityQueue<DstarNode>();
		start = dstarMap.getStart();
		currentRobotPosition = dstarMap.getRobotLocation();
		goal = dstarMap.getGoal();
		buildCostTable();
	}

	/**
	 * todo:call travelsMap to find optimal path
	 */
	public void travelsMap() {
		// minimumK will be used to store what is returned from processState()
		double minimumK;
		//add the goal to the openList
		insetToOpenList(goal, 0);
		dstarMap.print();
		// print the map as initialized and prompt user to start next seciont
		int userInput;
		try {
			System.out.println("Map has been initialized!");
			System.out.println("Ready to begin (press enter):");
			userInput = System.in.read();
		} catch (Exception e) {
		}
		//loop process state until minimumK == -1 or that start state is closed
		do {
			minimumK = processState();
		} while (minimumK != -1.0 && !start.getTag().equalsIgnoreCase("closed"));
		//after processing initial loop of process state,print the current state of the dstarMap
		try {
			dstarMap.print();
			System.out.println("Map has been expanded from goal...");
			System.out.println("We will either begin tracing to goal, or the goal is unreachable (press enter):");
			userInput = System.in.read();
		} catch (Exception e) {
		}
		//if minimumK==-1 then goal is unreachable
		if (minimumK == -1) {
			System.out.println("Goal is unreachable...");
			System.exit(0);
		} else {
			//start tracing path through back pointers,trace(追溯) until the goal is reached or the next is unknown
			do {
				DstarNode next;
				DstarNode here;
				boolean unknownFound = false;
				dstarMap.print();

				do {
					//start from where to robot is current located
					here = dstarMap.getRobotLocation();
					//get next node from current location back pointer
					next = here.getBackPointer();
					//is next state unknown?
					unknownFound = next.getState().equals("u");
					if (!unknownFound) {
						//move the robot
						dstarMap.setRobotLocation(next);
						currentRobotPosition = dstarMap.getRobotLocation();
						dstarMap.print();
						try {
							System.out.println("Robot Moved!!");
							System.out.println("Move again (press enter):");
							userInput = System.in.read();
						} catch (Exception e) {

						}

					}
				} while (currentRobotPosition != goal && !unknownFound);
				//if we reach the goal,exist
				if (currentRobotPosition == goal) {
					System.out.println("Goal has been reached!");
					System.exit(0);
				} else {
					//we are not at the goal yet,let user know we found an unknown obstacle(障碍)，prompt for input to continue
					try {
						System.out.println("Unknown Obstacle!!!");
						System.out.println("Find another way (press enter):");
						userInput = System.in.read();
					} catch (Exception e) {
					}
					//modify the cost from here to next to infinity
					modifyCost(here, next, INFINITY_COST);
					//loop process state again until minimumK < h(next) or minimumK == -1.0
					do {
						minimumK = processState();
					} while (minimumK < next.getH() && minimumK != -1.0 && !currentRobotPosition.getTag().equalsIgnoreCase("closed"));
					//*BUG FIX, we weren't stopping the process state when the current robot position was closed, this caused the bot to recalculate too much and overwrite the correct path backPointers*//&& !(currentRobotPosition.equals(openList.peek())));
					// if minimumK == -1.0 goal is unreachable, exit application
					if (minimumK == -1.0) {
						System.out.println("Goal is unreachable!");
						System.exit(0);
					}

					//print the map, wait for user input
					dstarMap.print();
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
	 * 用于计算到目标goal的最优路径
	 * process the current node and sets neighbor values as deemed necessary
	 * @return
	 */
	private double processState() {
		double kOld;
		List<DstarNode> neighbors;
		int userInput;
		// STEP 1
		// if open list is empty exit return -1
		if (openList.isEmpty()) { return -1.0; }
		if (currentRobotPosition.getK() == 10000 && currentRobotPosition.getH() == 10000) { return -1.0; }
		// get node from head of openList (with minK)
		DstarNode currentNode = openList.remove();
		kOld = currentNode.getK();//k_Old = Get Min K
		// and set it to closed	
		currentNode.setTag("closed");//Delete X

		// STEP 2 Re-routing if necessary
		// refers to L4 - L7 in paper by Stentz
		if (kOld < currentNode.getH()) {
			System.out.println("Step 2 - Rerouting - node: " + currentNode.getLabel());
			neighbors = dstarMap.getNeighbors(currentNode.getRow(), currentNode.getColumn());
			for (DstarNode neighbor : neighbors) {
				System.out.println("Updating Neighbor: " + neighbor.getLabel());
				double costXthroughY = neighbor.getH() + costs.getValue(neighbor.getLabel(), currentNode.getLabel());

				if (neighbor.getH() <= kOld && currentNode.getH() > costXthroughY) {
					System.out.println("-- step 2 -- set back pointer to " + neighbor.getLabel());
					System.out.println("current: " + currentNode.getLabel());

					currentNode.setBackPointer(neighbor);
					currentNode.setH(costXthroughY);
				}
			}
		}

		// Step 3 - usually done in initial map state processing
		// refers to L8 - L13 in paper by Stentz
		if (kOld == currentNode.getH()) {
			//System.out.println("Start Step 3");
			neighbors = dstarMap.getNeighbors(currentNode.getRow(), currentNode.getColumn());
			for (DstarNode neighbor : neighbors) {
				double costThroughX = currentNode.getH() + costs.getValue(currentNode.getLabel(), neighbor.getLabel());
				double roundedNumber = (double) Math.round(costThroughX * 10) / 10;
				if (neighbor.getTag().equalsIgnoreCase("NEW") || (neighbor.getBackPointer() == currentNode && neighbor.getH() != costThroughX) || (neighbor.getBackPointer() != currentNode && neighbor.getH() > costThroughX)) {

					neighbor.setBackPointer(currentNode);
					insetToOpenList(neighbor, roundedNumber);
				}
			}
		}

		// Step 4
		// reference to L14 - L25 in paper by Stentz
		else {
			neighbors = dstarMap.getNeighbors(currentNode.getRow(), currentNode.getColumn());
			for (DstarNode neighbor : neighbors) {
				double costThroughX = currentNode.getH() + costs.getValue(currentNode.getLabel(), neighbor.getLabel());
				double costXthroughY = neighbor.getH() + costs.getValue(neighbor.getLabel(), currentNode.getLabel());
				//double roundedCostThroughX = costThroughX;
				//	double roundedCostXThroughY = costXthroughY;

				double roundedCostThroughX = (double) Math.round(costThroughX * 10) / 10;
				double roundedCostXThroughY = (double) Math.round(costXthroughY * 10) / 10;
				double roundedH;

				// Step 5
				// refers to L16 - L18 paper by Stentz
				if (neighbor.getTag().equalsIgnoreCase("NEW") || (neighbor.getBackPointer() == currentNode && neighbor.getH() != costThroughX)) {

					//routeFirstViaSecond(neighbor,currentNode);
					neighbor.setBackPointer(currentNode);
					insetToOpenList(neighbor, roundedCostThroughX);

				} else if (neighbor.getBackPointer() != currentNode && neighbor.getH() > roundedCostThroughX) {
					roundedH = (double) Math.round(currentNode.getH() * 10) / 10;
					insetToOpenList(currentNode, roundedH);

				} else if (neighbor.getBackPointer() != currentNode && currentNode.getH() > roundedCostXThroughY && neighbor.getTag().equalsIgnoreCase("closed") && neighbor.getH() > kOld) {
					roundedH = (double) Math.round(neighbor.getH() * 10) / 10;
					insetToOpenList(neighbor, roundedH);
				}
			}
		}

		// set minK, of openList is not empty, then get the minK from open list
		double minK = -1.0;
		if (!openList.isEmpty()) {
			minK = openList.peek().getK();
		}
		try {

			System.out.println("Planning....");
			System.out.println("Current Path:");
			dstarMap.print();
			System.out.println("Press ENTER to continue...");
			userInput = System.in.read();
		} catch (Exception e) {

		}

		return minK;

	}

	/**
	 * routeFirstViaSecond from Dr. Kays notes, however we don't use this
	 *
	 * TODO remove
	 *
	 * @param Y
	 * @param X
	 */
	private void routeFirstViaSecond(DstarNode Y, DstarNode X) {
		Y.setBackPointer(X);
		Y.setH(X.getH() + costs.getValue(X.getLabel(), Y.getLabel()));
		if (Y.getTag().equalsIgnoreCase("new")) {
			Y.setK(Y.getH());
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

	private double modifyCost(DstarNode current, DstarNode neighbor, double newVal) {

		//costs.updateValue(current.getLabel(), neighbor.getLabel(), newVal);
		//discrepency.setH(INFINITY_COST);
		List<DstarNode> neighborList = dstarMap.getNeighbors(neighbor.getRow(), neighbor.getColumn());

		// update the arc path costs of neighbor to its neighbors
		for (DstarNode each : neighborList) {
			costs.updateValue(neighbor.getLabel(), each.getLabel(), INFINITY_COST);
		}

		//neighbor.setH(newVal);
		// if neight is closed, then add it back to the open list with new h value
		if (neighbor.getTag().equalsIgnoreCase("closed")) {
			insetToOpenList(neighbor, newVal);
		}

		// get the new minimum k value on the open list
		DstarNode currentMin = openList.peek();
		return currentMin.getK();
	}

	/**
	 * update a nodes K and H value and inserts/reinserts a node into the openList
	 * @param someNode
	 * @param newH
	 */
	private void insetToOpenList(DstarNode someNode, double newH) {
		if (newH > 10000) {
			newH = 10000;
		}
		double roundedH;
		double currentK;
		DstarNode temp;
		if (someNode.getTag().equalsIgnoreCase("new")) {
			someNode.setK(newH);
			someNode.setH(newH);
			someNode.setTag("open");
			openList.add(someNode);
		}
		if (someNode.getTag().equalsIgnoreCase("open")) {
			currentK = someNode.getK();
			openList.remove(someNode);
			someNode.setK(Math.min(currentK, newH));
			openList.add(someNode);
		}
		if (someNode.getTag().equalsIgnoreCase("closed")) {
			int userInput;
			roundedH = (double) Math.round(someNode.getH() * 10) / 10;
			someNode.setK(Math.min(roundedH, newH));
			someNode.setH(newH);
			someNode.setTag("open");
			openList.add(someNode);
		}
	}

	/**
	 * Initialized the cost table
	 *
	 * todo a lot of this is repative, and should be fixed
	 * neighborOutOfBounds should be a public method in DstarMap
	 */
	private void buildCostTable() {
		/**
		 * loop all nodes,set cost between open nodes to 1 or 1.4
		 */
		int curX = 0;
		int curY = 0;
		DstarNode neighbor;
		for (int i = 0; i < dstarMap.getRows(); i++) {
			for (int j = 0; j < dstarMap.getColumns(); j++) {
				DstarNode current = dstarMap.getNode(i, j);
				int top = i - 1;
				int bottom = i + 1;
				int left = j - 1;
				int right = j + 1;
				if (!neighborOutOfBounds(top, left)) {
					neighbor = dstarMap.getNode(top, left);
					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
					}
				}
				// direct top neighbor
				if (!neighborOutOfBounds(top, j)) {
					neighbor = dstarMap.getNode(top, j);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
					}
				}

				// top right neighbor
				if (!neighborOutOfBounds(top, right)) {
					neighbor = dstarMap.getNode(top, right);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
					}
				}

				// left direct neighbor
				if (!neighborOutOfBounds(i, left)) {
					neighbor = dstarMap.getNode(i, left);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
					}
				}

				// right direct neighbor
				if (!neighborOutOfBounds(i, right)) {
					neighbor = dstarMap.getNode(i, right);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
					}
				}

				// bottom left neighbor
				if (!neighborOutOfBounds(bottom, left)) {
					neighbor = dstarMap.getNode(bottom, left);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
					}
				}

				// direct bottom neighbor
				if (!neighborOutOfBounds(bottom, j)) {
					neighbor = dstarMap.getNode(bottom, j);

					if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
						costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
					} else {
						costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
					}
				}

				// bottom right neighbor
				if (!neighborOutOfBounds(bottom, right)) {
					neighbor = dstarMap.getNode(bottom, right);

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
		System.out.println(node.getLabel() + "\t\t\t" + String.format("% ,.1f", node.getK()) + "\t\t" + String.format("% ,.1f", node.getH()));

		System.out.println("List Head\t\tK\t\tH");
		System.out.println("------------------------------------------------");
		for (DstarNode n : list) {
			System.out.println(n.getLabel() + "\t\t\t" + String.format("% ,.1f", n.getK()) + "\t\t" + String.format("% ,.1f", n.getH()));
		}
	}

	private boolean neighborOutOfBounds(int row, int column) {
		boolean outofbounds = false;
		if (row < 0 || row >= dstarMap.getRows() || column < 0 || column >= dstarMap.getColumns()) {
			outofbounds = true;
		}

		return outofbounds;
	}
}
