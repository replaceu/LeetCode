package com.carter.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * 数独的算法要求
 * 1.在一个9*9的矩阵中填入1-9的数字
 * 2.在横向上不能够有数字重复
 * 3.在纵向上不能够有数字重复
 * 4.数字1-9在每一个以粗实线分隔的3x3宫内只能出现一次
 */
public class SudokuAlgorithm {

	public static void main(String[] args) {
		int[][] map = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 0, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 }, { 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 }, { 7, 0, 0, 0, 2, 0, 0, 0, 6 }, { 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 }, };
		SudokuNode sudokuNode = new SudokuNode();
		int[][] sudokuBoard = sudokuNode.solveSudoku(map);
		// 地图形式
		for (int i = 0; i < sudokuBoard[0].length; i++) {
			System.out.print("\t" + i + ",");
		}
		System.out.print("\n-----------------------------------------\n");
		int count = 0;

		for (int i = 0; i < sudokuBoard.length; i++) {
			for (int j = 0; j < sudokuBoard[0].length; j++) {
				if (j == 0) System.out.print(count++ + "|\t");
				System.out.print(sudokuBoard[i][j] + ",\t");
			}
			System.out.println();
		}
		System.out.println();
	}

}

class SudokuNode {

	private List<Integer> leftList = new ArrayList<>();

	private List<Integer> usedList = new ArrayList<>();

	private int countSum = 0;

	public int[][] solveSudoku(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					List<Integer> usedList = getUsedList(board, i, j);
					List<Integer> leftList = getLeftList(usedList);
					if (leftList.size() == 1) {
						board[i][j] = leftList.get(0);
						usedList.clear();
						leftList.clear();
					} else {
						usedList.clear();
						leftList.clear();
					}
				}else {
					countSum++;
				}
			}
		}
		//todo:退出条件
		if (countSum < 81) {
			countSum=0;
			board = solveSudoku(board);
		}
		return board;

	}

	public List<Integer> getUsedList(int[][] broad, int m, int n) {
		for (int j = 0; j < broad[m].length; j++) {
			if (broad[m][j] > 0) {
				usedList.add(broad[m][j]);
			}
		}
		for (int i = 0; i < broad.length; i++) {
			if (broad[i][n] != 0 && !usedList.contains(broad[i][n])) {
				usedList.add(broad[i][n]);
			} else {
				continue;
			}

		}
		int middleM = getNearPoint(m);
		int middleN = getNearPoint(n);
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (broad[middleM + i][middleN + j] != 0 && !usedList.contains(broad[middleM + i][middleN + j])) {
					usedList.add(broad[middleM + i][middleN + j]);
				} else {
					continue;
				}
			}
		}

		return usedList;
	}

	private int getNearPoint(int m) {
		Integer p = null;
		if (m >= 0 && m <= 2) {
			p = 1;
		} else if (m >= 3 && m <= 5) {
			p = 4;
		} else {
			p = 7;
		}
		return p;
	}

	public List<Integer> getLeftList(List<Integer> usedList) {
		for (int i = 1; i <= 9; i++) {
			if (!usedList.contains(i)) {
				leftList.add(i);
			}
		}
		return leftList;
	}
}
