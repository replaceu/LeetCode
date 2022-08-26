package com.carter.structure;

import java.util.Scanner;

public class CircleArrayQueue {

    public static void main(String[] args) {
        ArrayQueueCircle queueArr = new ArrayQueueCircle(5);

        char key=' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop =true;
        while (loop){
            System.out.println("s(show):显示队列");
            System.out.println("e(esit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列取出数据");
            System.out.println("h(head):查看队列头的数据");
            key = scanner.next().charAt(0);
            switch (key){
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
                        System.out.printf("取出的数据是%d\n",res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try{
                        int res = queueArr.headQueue();
                        System.out.printf("队列头的数据是%d\n",res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop=false;
                    break;
                default:
                    break;
            }
        }

    }
}

/**
 * 1.front变量的含义做一个调整：front就指向队列的第一个元素，也就是说arr[front]就是队列的第一个元素,front的初始值=0
 * 2.end变量的含义做一个调整，end指向队列的最后一个元素的后一个位置，因为希望空出一个空间作为约定，end的初始值=0
 * 3.当队列满时，条件是（end+1）%maxSize=front
 * 4.当队列为空的条件，end==front
 * 5.当我们这样分析，队列中有效的数据的个数(end+maxSize-front)&maxSize*/

class ArrayQueueCircle{

    private int maxSize;//表示数组的最大容量
    private int front;
    private int end;
    private int[] arr;//该数组用于存放数据，模拟队列
    public ArrayQueueCircle(int arrMaxSize){
        maxSize = arrMaxSize;
        arr = new int[maxSize];

    }

    //判断是否满
    public boolean isFull(){
        return (end+1)%maxSize==front;
    }
    //判断是否为空
    public boolean isEmpty(){
        return end==front;
    }
    //添加数据到队列
    public void addQueue(int n){
        if (isFull()){
            System.out.println("队列满，不能加入数据！");
            return;
        }
        //直接将数据加入就可以了
        arr[end]=n;
        end = (end+1)%maxSize;
    }

    //获取队列的数据，出队列
    public int getQueue(){
        //判断是否为空
        if (isEmpty()){
            throw new RuntimeException("队列为空，无法取出数据");
        }
        //这里需要分析出front是指向队列的第一个元素
        //1.先把front对应的值保存到一个临时变量
        //2.将front后移
        //3.将临时保存的变量返回
        int value = arr[front];
        front = (front+1)%maxSize;
        return value;
    }
    //显示队列的所有数据
    public void showQueue(){
        if (isEmpty()){
            System.out.println("队列是空的，没有数据");
            return;
        }
        //从front开始遍历，遍历多少个元素
        for (int i = front; i <front+size() ; i++) {
            System.out.printf("arr[%d]=%d\n",i%maxSize,arr[i%maxSize]);
        }

    }

    public int size(){
        return (end+maxSize-front)%maxSize;
    }
    //显示头元素
    public int headQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列为空，没有数据");
        }

        return arr[front];
    }

}
