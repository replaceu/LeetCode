package com.carter.structure;

import java.util.Scanner;

/**
 * 用数组模拟队列
 */
public class ArrayQueue {

	public static void main(String[] args) {
		QueueArr queueArr = new QueueArr(5);

		char key = ' ';
		Scanner scanner = new Scanner(System.in);
		boolean loop = true;
		while (loop) {
			System.out.println("s(show):显示队列");
			System.out.println("e(exit):退出程序");
			System.out.println("a(add):添加数据到队列");
			System.out.println("g(get):从队列取出数据");
			System.out.println("h(head):查看队列头的数据");
			key = scanner.next().charAt(0);
			switch (key) {
			case 's':
				queueArr.showQueue();
				break;
			case 'a':
				System.out.println("输入一个数");
				int value = scanner.nextInt();
				queueArr.addQueue(value);
				break;
			case 'g':
				try {
					int res = queueArr.getQueue();
					System.out.printf("取出的数据是%d\n", res);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 'h':
				try {
					int res = queueArr.headQueue();
					System.out.printf("队列头的数据是%d\n", res);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 'e':
				scanner.close();
				loop = false;
				break;
			default:
				break;
			}
		}

		System.out.println(queueArr.headQueue());

	}
}

class QueueArr {

	private int		maxSize;//表示数组的最大容量
	private int		front;	//队列头
	private int		end;	//队列尾
	private int[]	arr;	//该数组用于存放数据，模拟队列

	//创建队列的构造器
	public QueueArr(int maxArrSize) {
		maxSize = maxArrSize;
		arr = new int[maxSize];
		front = -1;//指向队列头部，分析出front是指向队列头的前一个位置
		end = -1;//指向对列尾的数据（就是队列的最后一个数据）
	}

	//判断队列是否满的方法
	public boolean isFull() {
		return end == maxSize - 1;
	}

	//判断队列是否为空的方法
	public boolean isEmpty() {
		return end == front;
	}

	//添加数据到对列
	public void addQueue(int n) {
		//1.判断队列是否满
		if (isFull()) {
			System.out.println("队列已满，无法加入");
			return;
		}
		end++;

		arr[end] = n;
	}

	//获取队列的数据，出队列
	public int getQueue() {
		//1.判断队列是否为空
		if (isEmpty()) { throw new RuntimeException("队列为空，不能取出数据"); }

		front++;
		return arr[front];
	}

	//显示队列的所有数据
	public void showQueue() {
		if (isEmpty()) {
			System.out.println("队列为空，没有数据");
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.printf("arr[%d]=%d\n", i, arr[i]);
		}
	}

	//显示队列的头数据，注意不是取出数据
	public int headQueue() {
		if (isEmpty()) { throw new RuntimeException("队列是空的，没有数据"); }
		return arr[front + 1];
	}
}

//使用数住模拟环形队列的思路分析
/**
 * 1.front变量的含义做一个调整：front就指向队列的第一个元素，也就是说arr[front]就是队列的第一个元素,front的初始值=0
 * 2.end变量的含义做一个调整，end指向队列的最后一个元素的后一个位置，因为希望空出一个空间作为约定，end的初始值=0
 * 3.当队列满时，条件是（end+1）%maxSize=front
 * 4.当队列为空的条件，end==front
 * 5.当我们这样分析，队列中有效的数据的个数(end+maxSize-front)%maxSize*/