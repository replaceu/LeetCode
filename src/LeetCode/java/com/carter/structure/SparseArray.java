package com.carter.structure;

/**
 * 稀疏数组
 */
public class SparseArray {

    public static void main(String[] args) {

        //1.创建一个11*11的原始二维数组

        int[][] chessArr = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        chessArr[7][9] = 14;
        //输出原始的二维数组

        for (int[] row : chessArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        //将二维数组转化为稀疏数组的思路
        //1.先遍历二维数组，得到非0数据的个数

        int sum = 0;
        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr.length; j++) {
                if (chessArr[i][j] != 0) {
                    sum++;
                }
            }

        }
        //2.创建稀疏数组
        int sparseArr[][] = new int[sum + 1][3];

        //给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;

        //遍历二维数组，将非0的值存放到稀疏数组中

        int count = 0;
        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr.length; j++) {
                if (chessArr[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];

                }
            }
        }
        //输出稀疏数组
        System.out.println();
        System.out.println("得到的稀疏数组为：");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }
        System.out.println();

        //将稀疏数组恢复到原始二维数组
        //1.先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        int[][] chessOriginal = new int[sparseArr[0][0]][sparseArr[0][1]];

        //2.遍历稀疏数组
        for (int i = 1; i < sparseArr.length; i++) {

            chessOriginal[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        for (int[] row : chessOriginal) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

    }
}
