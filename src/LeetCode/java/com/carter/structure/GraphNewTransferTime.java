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
    public static void main(String[] args) {

    }

    private List<Node> findDelayPath(Node start, Node end){
        List<Node> res = new ArrayList<>();
        depthFirstSearch(0,0,start,end,0,res);
        return res;
    }

    private void depthFirstSearch(int i,int j,Node start, Node end, int delay,List<Node> res) {
        Node current = new Node(i, j, delayMap[i][j]);
        boolean flag = current.delay==current.parent.delay;
        if (i==end.x&&j== end.y){
            delay+=current.delay-(flag?1:0);
            current.setDelay(delay);
            res.add(current);
            return;
        }

        for (int[] direction : directions) {
            int newI = i + direction[0];
            int newJ = j + direction[1];
            Node position = new Node(newI, newJ, delayMap[newI][newJ]);

        }


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
