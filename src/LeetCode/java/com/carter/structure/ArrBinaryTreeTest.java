package com.carter.structure;

//顺序二叉树
public class ArrBinaryTreeTest {
	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 4, 5, 6, 7 };
		ArrBinaryTree tree = new ArrBinaryTree(arr);
		tree.preOrder();
	}
}

//todo:编写一个ArrayBinaryTree,实现顺序存储二叉树遍历
class ArrBinaryTree {
	//存储数据节点的数组
	private int[] arr;

	public ArrBinaryTree(int[] arr) {
		this.arr = arr;
	}

	public void preOrder() {
		this.preOrder(0);
	}

	public void preOrder(int index) {
		//如果数组为空或者数组长度为0
		if (arr == null || arr.length == 0) {
			System.out.println("数组为空，不能按照二叉树的前序遍历");
		}
		//输出当前这个元素
		System.out.println(arr[index]);
		//向左递归遍历
		if (index * 2 + 1 < arr.length) {
			preOrder(2 * index + 1);
		}
		//向右递归遍历
		if (index * 2 + 2 < arr.length) {
			preOrder(2 * index + 2);
		}
	}

}