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
    public static final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1},{-1,1},{1,1},{-1,-1},{1,-1}};
    public static final int[][] delayMap = {{0,2,2},{1,2,1},{2,2,1}};

    public List<Node> openList = new ArrayList<>();
    public List<Node> closeList = new ArrayList<>();
    public static void main(String[] args) {
        Node start = new Node(0, 0, 0);
        Node end = new Node(2, 2, 1);
        printMap(delayMap);
        GraphNewTransferTime graphNewTransferTime = new GraphNewTransferTime();
        graphNewTransferTime.findDelayPath(start,end,0);

    }

    public Node findDelayPath(Node start, Node end,int delay){
        //todo:将起点加入openList
        openList.add(start);
        while (openList.size()>0){
            //todo:寻找当前延时值最小的点
            Node currentNode = findMinDelayOpenList();
            //todo:从openList中移除
            openList.remove(currentNode);
            closeList.add(currentNode);
            List<Node> neighborNodeList = getNeighborNodeList(currentNode);
            for (Node neighbour : neighborNodeList) {
                if (exist(openList,neighbour.x,neighbour.y)){
                    if (neighbour.delay== currentNode.delay){
                        delay = delay+ neighbour.delay-1;
                    }else {
                        delay = delay+ neighbour.delay;
                    }
                }else {
                    if (neighbour.delay== currentNode.delay){
                        delay = delay+ neighbour.delay-1;
                    }else {
                        delay = delay+ neighbour.delay;
                    }
                    openList.add(neighbour);
                }
            }
            //如果终点在openList中，则代表找到路径
            if (findPath(openList,end)!=null){
                return findPath(openList,end);
            }
        }
        return findPath(openList,end);
    }

    private Node findPath(List<Node> openList, Node end) {
        for (Node node : openList) {
            if (node.x== end.x&&node.y== end.y){
                return node;
            }
        }
        return null;
    }

    private List<Node> getNeighborNodeList(Node currentNode) {
        List<Node> nodeList = new ArrayList<>();
        for (int[] direction : directions) {
            int neighborX = currentNode.x + direction[0];
            int neighborY = currentNode.y + direction[1];
            if (checkPath(neighborX,neighborY)&&!exist(openList,neighborX,neighborY)){
                nodeList.add(new Node(neighborX,neighborY,delayMap[neighborX][neighborY]));
            }
        }
        return nodeList;
    }

    private boolean exist(List<Node> openList, int neighborX, int neighborY) {
        for (Node node : openList) {
            if (node.x==neighborX&&node.y==neighborY){
                return true;
            }
        }
        return false;
    }

    private Node findMinDelayOpenList() {
        Node tmpNode = openList.get(0);
        for (Node node : openList) {
            if (node.delay< tmpNode.delay){
                tmpNode = node;
            }
        }
        return tmpNode;
    }

    private static boolean checkPath(int x,int y) {
        if (x>=0&&y>=0&&x<delayMap.length&&y<delayMap[0].length){
            return true;
        }
        return false;
    }

    /**
     * 打印地图
     * @param maps
     */
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
}

class Node{
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
