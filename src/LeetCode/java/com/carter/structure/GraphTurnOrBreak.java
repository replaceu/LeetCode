package com.carter.structure;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Jungle 生活在美丽的蓝鲸城，大马路都是方方正正，但是每天马路的封闭情况都不一
 * 样地图由以下元素组成:
 * 1)"0"一 空地，可以达到
 * 2)"1"一 路障，不可达到:
 * 3)"s"- Jungle 的家
 * 4)"t"一公司.
 * 其中我们会限制 Jungle 拐弯的次数，同时 Jungle 可以清除给定个数的路障，现在你的
 * 任务是计算 Jungle 是否可以从家里出发到达公司。
 */
public class GraphTurnOrBreak {

    static int[][] offsets =  {{-1, 0, 1}, {1, 0, 2}, {0, -1, 3}, {0, 1, 4}};
    static int turnChance,breakChance,n,m;
    static String[][] matrix;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        turnChance = scanner.nextInt();
        breakChance = scanner.nextInt();

        n = scanner.nextInt();
        m = scanner.nextInt();

        matrix = new String[n][m];
        for (int i = 0; i < n; i++) {
            matrix[i] = scanner.next().split("");
        }
        System.out.println(getResult());

    }
    public static String getResult(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if ("s".equals(matrix[i][j])){
                    HashSet<Integer> path = new HashSet<>();
                    path.add(i*m+j);
                    return depthFirstSearch(i,j,0,0,0,path)?"Yes":"No";
                }
            }
        }
        return "No";
    }

    /**
     *
     * @param currentI 当前位置横坐标
     * @param currentJ 当前位置纵坐标
     * @param usedTurn 已拐弯次数
     * @param usedBreak 已破壁次数
     * @param lastDirect 上一次运动方向，初始为0，表示第一次运动不会造成拐弯
     * @param path 行动路径，用于记录走过的位置，避免走老路
     * @return 终点是否可达
     */

    public static boolean depthFirstSearch(int currentI, int currentJ, int usedTurn, int usedBreak, int lastDirect, HashSet<Integer> path){
        if ("t".equals(matrix[currentI][currentJ])){
            return true;
        }
        //todo:有四个方向选择走下一步
        for (int[] offset : offsets) {
            int direct = offset[2];
            int newI = currentI + offset[0];
            int newJ = currentJ + offset[1];
            //flagTurn记录本次运动是否拐弯
            boolean flagTurn = false;
            //flagBreak记录本次运动是否破壁
            boolean flagBreak = false;
            //todo:如果下一步没有越界，则进一步检查
            if (newI>=0&&newI<n&&newJ>=0&&newJ<m){
                //todo:如果下一步位置已经走过了则是老路，可以不走
                int position = newI*m+newJ;
                if (path.contains(position)){
                    continue;
                }
                //todo:如果下一步位置需要拐弯
                if (lastDirect!=0&&lastDirect!=direct){
                    //如果拐弯次数用完了，则不走
                    if (usedTurn+1>turnChance){
                        continue;
                    }
                    flagTurn = true;
                }
                //todo:如果下一步需要破壁
                if ("1".equals(matrix[newI][newJ])){
                    //如果破壁次数用完了，则不走
                    if (usedBreak+1>breakChance){
                        continue;
                    }
                    flagBreak = true;
                }
                //todo:记录走过的位置
                path.add(position);
                //todo:继续下一步递归
                boolean result = depthFirstSearch(newI, newJ, usedTurn + (flagTurn ? 1 : 0), usedBreak + (flagBreak ? 1 : 0), direct, path);
                //todo:如果某路径可以在给定的usedTurn,usedBreak下，到达目的地target，则返回 true
                if (result){
                    return true;
                }
                //todo：否则回溯
                path.remove(position);
            }
        }

        return false;

    }
}
