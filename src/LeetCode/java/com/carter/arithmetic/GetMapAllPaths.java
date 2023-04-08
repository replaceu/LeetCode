package com.carter.arithmetic;



import java.util.ArrayList;
import java.util.List;

/**
 * 找到一张图上从起点到终点的所有路径
 */
public class GetMapAllPaths {
    public static void main(String[] args) {
        Map map = new Map(3);
        map.isVisited[0][0] = true;
        int[][]	delayMap	= { { 0, 2, 2 }, { 1, 2, 1 }, { 2, 2, 1 } };
        map.getAllPaths(new PathNode(0,0),0,delayMap);
    }

}
class Map{
    boolean[][] isVisited;

    int[][] path;

    public static final int[][]	directions	= { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 1 }, { 1, 1 }, { -1, -1 }, { 1, -1 } };

    Map(int n){
        isVisited = new boolean[n][n];
        path = new int[n*n][3];
    }

    public void getAllPaths(PathNode current, int n,int[][] delayMap) {
        path[n][1] = current.x;
        path[n][2] = current.y;
        if (current.x==delayMap[0].length-1&&current.y==delayMap.length-1){
            printPath(n);
        }
        for (int i = 0; i < 8; i++) {
            int newX = current.x + directions[i][0];
            int newY = current.y + directions[i][1];
            //todo：如果新位置越界，或者新位置已经扫描过，则停止递归
            if (newX>=0&&newX<delayMap[0].length&&newY>=0&&newY<delayMap.length&&isVisited[newX][newY]!=true){
                PathNode newNode = new PathNode(newX, newY);
                isVisited[newX][newY]=true;
                getAllPaths(newNode,n+1,delayMap);
                isVisited[newX][newY]=false;
            }

        }
    }

    public void printPath(int n){
        for (int i = 0; i < n; i++) {
            System.out.print("("+path[i][1]+","+path[i][2]+")"+"->");

        }
        System.out.println("("+path[n][1]+","+path[n][2]+")");
    }
}

class PathNode {
    int x;

    int y;

    public PathNode(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
