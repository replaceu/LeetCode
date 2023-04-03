package com.carter.structure;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 有 M*N 的节点短阵，每个节点可以向 8 个方向(上、下、左、右及四个斜线方向)转发
 * 数据包，每个节点转发时会消耗固定时延，连续两个相同时延可以减少一个时延值(即当有
 * K 个相同时延的节点连续转发时可以减少 K-1 个时延值)，
 * 求左上角 (0，0)开始转发数据包到右下角 (M-1，N- 1)并转发出的最短时延
 */
public class GraphTransferTime {
    static int[][] matrix;
    static int m;
    static int n;
    static int[][] offsets =  {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    public static void main(String[] args) {

    }

    public static int getResult(int[][] matrix){
        List<Integer> res = new ArrayList<>();
        HashSet<Integer> path = new HashSet<>();
        path.add(0);

        depthFirstSearch(0,0,0,Integer.MAX_VALUE,path,res);
        return res.stream().min((a, b) -> a - b).get();
    }

    /**
     * @param i 横坐标
     * @param j 纵坐标
     * @param delay 已累计的时间延值
     * @param last 上一个节点的时延值，用于和当前节点时延值对比，若相同，则新增时延值-1
     * @param path 记录扫描过的节点的位置，避免重复扫描
     * @param res 记录各种从起点到终点的路径的时延值
     */
    public static void depthFirstSearch(int i,int j,int delay,int last,HashSet<Integer> path,List<Integer> res){
        //todo:当前节点的延时值
        int current = matrix[i][j];
        // todo:flag 用于标记，当前节点和上一个节点的时延值是否相同，若相同，则新增的时延值要-1
        boolean flag = current==last;
        //todo:如果搜索到了最后一个点,那么就将该路径的时延计算出来,加入到 res中，结束分支递归
        if (i==m-1&&j==n-1){
            delay+=current-(flag?1:0);
            res.add(delay);
            return;
        }
        //深度优先搜索当前点的八个方向
        for (int[] offset : offsets) {
            int newI = i + offset[0];
            int newJ = j + offset[j];
            //todo:将二维坐标，转成一维坐标 pos
            int position = newI*m+newJ;
            //todo：如果新位置越界，或者新位置已经扫描过，则停止递归
            if (newI>=0&&newI<m&&newJ>=0&&newJ<n&&!path.contains(position)){
                path.add(position);
                depthFirstSearch(newI,newJ,delay+current-(flag?1:0),current,path,res);
                path.remove(position);
            }

        }

    }
}
