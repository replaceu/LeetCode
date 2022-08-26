package com.carter.structure;

/**
 * 约瑟夫问题
 * 设编号为1，2，...,n的n个人围坐一圈，约定编号为k（1<=k<=n）的人从1开始报数，
 * 数到m的那个人出列，他的下一位又从1开始报数，数到m的那个人出列，以此类推，
 * 直到所有人出列为止，由此产生一个出队编号的序列
 *
 * 构建一个单向环形链表思路
 * 1.县创建第一个节点，让first指向该节点，并形成环形
 * 2.后面当我们没创建一个新的节点，就把该节点，加入到已有的环形链表当中即可
 * 遍历环形链表
 * 1.先让一个辅助指针（变量），指向first节点
 * 2.让后通过一个while循环遍历该环形链表即可
 * 3.遍历结束条件curBoy.next==first
 */
public class Josephu {
	public static void main(String[] args) {
		//测试
		CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
		circleSingleLinkedList.addBoy(5);
		circleSingleLinkedList.showBoy();
	}
}

//创建一个环形的单向链表
class CircleSingleLinkedList {
	//创建一个first节点，当前没有编号
	private Boy first = new Boy(-1);

	//添加节点，构成一个环形的链表
	public void addBoy(int num) {
		//对num做校验
		if (num < 1) {
			System.out.println("num的值不正确！");
			return;
		}
		//辅助指针，帮助我们来创建环形链表
		Boy curBoy = null;
		//使用for循环来创建环形链表
		for (int i = 1; i < num; i++) {
			//根据编号创建节点
			Boy boy = new Boy(i);
			//如果是第一个节点
			if (i == 1) {
				first = boy;
				//构成环状
				first.setNext(first);
				curBoy = first;//让curBoy指向第一个节点
			} else {
				curBoy.setNext(boy);
				boy.setNext(first);
				curBoy = boy;
			}
		}
	}

	//遍历当前环形列表
	public void showBoy() {
		//判断链表是否空
		if (first == null) {
			System.out.println("链表为空！");
			return;
		}
		//因为first不能动，所有要使用辅助指针
		Boy curBoy = first;
		while (true) {
			System.out.printf("小孩的编号%d \n", curBoy.getNo());
			if (curBoy.getNext() == first) {
				break;
			}
			curBoy = curBoy.getNext();//curBoy后移
		}
	}
}

//创建一个Boy类，表示一个节点
class Boy {
	private int	no;		//编号
	private Boy	next;	//指向下一个节点

	public Boy(int no) {
		this.no = no;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Boy getNext() {
		return next;
	}

	public void setNext(Boy next) {
		this.next = next;
	}
}