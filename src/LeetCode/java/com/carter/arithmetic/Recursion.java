package com.carter.arithmetic;

/**
 * 递归:就是方法自己调用自己，每次调用时传入不同的变量，有助于解决复杂的问题，同时可以使代码变得简洁
 * 递归调用规则：
 * 1.当程序执行到一个方法,就会开辟一个独立的空间（栈）
 * 2.方法的局部变量是独立的，不会相互影响
 * 3.递归必须向退出递归的条件逼近，否则就是无限递归了
 * 4.当一个方法执行完毕，或者遇到return就会返回，遵守谁调用就将结果返回给谁，同时当方法执行完毕或者返回时，该方法也就执行完毕
 * 5.如果方法中使用的是引用类型变量（比如数组），就会共享该引用类型的数据
 * f(n)=n(n-1)(n-2)...1=n!(阶乘问题)
 * 递归用于解决什么样的问题：
 * 1.各种数学问题，例如八皇后问题，汉诺塔问题，迷宫问题，球和篮子问题
 * 2.各种算法也会使用到递归，快排，归并排序，二分查找，分治算法
 */
public class Recursion {

    /**
     * 阶乘问题
     * @param n
     * @return
     */
    public static int factorial(int n){
        if (n==1){
            return n;
        }else {
            return factorial(n-1)*n;
        }
    }

    /**
     * 球和篮子问题，假设有两个篮子和两个球，那么放到两个篮子就有3中方法(1,1),(2,0),(0,2)
     * @param baskets 篮子的数量
     * @param capacity 篮子的最大容量
     * @param balls 球的数量
     * @return
     */
    public int countWays(int baskets,int capacity,int balls){
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(factorial(3));
    }
}
