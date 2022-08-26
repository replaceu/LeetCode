package com.carter.structure;

/**
 * 栈的应用场景
 * 1.子程序的调用：在跳往子程序前，会先将下个指令的地址存到栈中，直到子程序执行完后再将地址取出，以回到原来的程序中
 * 2.处理递归调用：和子程序的调用类似，只是除了存储下一个指令的地址外，也将参数、区域变量等数据存入堆栈
 * 3.表达式的转换：（中缀表达式转后缀表达式）与求值（实际解决）
 * 4.二叉树的遍历
 * 5.图形的深度优先搜索法
 *
 * 用数组来模拟栈的思路分析
 * 1.定义一个top来表示栈顶，初始化为-1
 * 2.入栈的操作，当有数据加入到栈，top++,stack[top]=data
 * 3.出栈的操作，int value = stack[top],top--,return value
 *
 * 使用栈完成计算一个表达式的结果
 * 1.创建两个栈数栈numStack和符号栈operationStack,分别存放数据和符号
 * 2.通过一个index索引，来遍历表达式
 * 3.如果我们发现是一个数字，就直接入数栈
 * 4.如果发现是一个符号，就分如下两种情况
 *   4.1如果当前的符号栈为空，就直接入栈
 *   4.2如果符号栈有操作符，就进行比较，如果当前的操作符优先级小于或者等于栈中的操作符，就需要从数栈中pop出两个数，在从符号栈中pop出一个符号，进行运算，将得到的结果入数栈，然后将当前的操作符入符号栈
 *   4.3如果当前的操作符优先级大于栈中的操作符,就直接进入符号栈
 * 5.当表达式扫描完成，就顺序的从数栈和符号栈pop出相应的数和符号，并运算
 * 6.最后在数栈只有一个数字，就是表达式的结果
 */
public class StackTest {
    public static void main(String[] args) {
        String expression = "4+2*10-2";
        //创建两个栈
        ArrayStack numStack = new ArrayStack(10);
        ArrayStack operationStack = new ArrayStack(10);
        //定义需要的相关变量
        int index=0;
        int num1 =0;
        int num2 = 0;
        int res = 0;
        int operation = 0;
        char ch=' ';
        String keepNum="";
        //开始使用while循环扫描expression
        while (true){
            //依次得到expression
            ch = expression.substring(index,index+1).charAt(0);
            //判断ch是什么,如果是一个运算符
            if (operationStack.isOperation(ch)){
                //判断当前的符号栈是否为空
                if (!operationStack.isEmpty()){
                    if (operationStack.priority(ch)<=operationStack.priority(operationStack.pick())){
                        num1=numStack.pop();
                        num2=numStack.pop();
                        operation = operationStack.pop();
                        res = numStack.compute(num1,num2,operation);
                        //将运算的结果入数栈
                        numStack.push(res);
                        //将当前的操作符入符号栈
                        operationStack.push(ch);
                    }else {
                       //如果当前的操作符优先级大于栈中的操作符,就直接进入符号栈
                        operationStack.push(ch);
                    }
                }else {
                    //如果为空就直接入符号栈
                    operationStack.push(ch);
                }
            }else {
                /*如果是数，则直接入数栈,ASCL编码表，
                当处理多位数时，不能发现一个数就立即入栈，
                需要看expression的index的下一位是不是符号
                因此需要定义一个变量字符串用于拼接多位数**/
                //numStack.push(ch-48);
                keepNum+=ch;
                //判断ch是不是expression的最后一位
                if (index==expression.length()-1){
                    numStack.push(Integer.parseInt(keepNum));
                }else {
                    //如果是数字就继续扫描，如果是运算符,就入栈
                    if (operationStack.isOperation(expression.substring(index+1,index+2).charAt(0))){
                        numStack.push(Integer.parseInt(keepNum));
                        //重要的清空keepNum
                        keepNum="";
                    }
                }
            }
            //让index+1,并判断是否扫描到expression最后
            index++;
            if (index>=expression.length()){
                break;
            }
        }
        //当表达式扫描完成，就顺序的从数栈和符号栈pop出相应的数和符号，并运算
        while (true){
            //如果符号栈为空，则计算到最后的结果，数栈中只有一个结果
            if (operationStack.isEmpty()){
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            operation = operationStack.pop();
            res = numStack.compute(num1,num2,operation);
            numStack.push(res);
        }
        System.out.printf("表达式%s=%d",expression,numStack.pop());

    }
}

//定义一个类ArrayStack表示栈
class ArrayStack{
    private int maxSize;//栈的大小
    private int[] stack;//数据就放在该数组
    private int top =-1;//栈顶，初始化为-1
    //构造器
    public ArrayStack(int maxSize){
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }
    //栈满
    public boolean isFull(){
        return top ==maxSize-1;
    }
    //栈空
    public boolean isEmpty(){
        return top==-1;
    }
    //入栈
    public void push(int value){
        if (isFull()){
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top]=value;
    }
    //出栈
    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("栈空");
        }
        int value = stack[top];
        top--;
        return value;
    }
    //显示栈的情况，遍历栈
    public void list(){
        if (isEmpty()){
            System.out.println("栈空");
        }
        for (int i = top; i < stack.length; i--) {
            System.out.printf("stack[%d]=%d/n",i,stack[i]);
        }
    }
    //返回运算符的优先级，优先级使用数字表示，数字越大，则优先级越高
    public int priority(int operation){
        if (operation=='*'||operation=='/'){
            return 1;
        }else if (operation=='+'||operation=='-'){
            return 0;
        }else{
            return -1;
        }
    }
    //判断是不是一个运算符
    public boolean isOperation(char value){
        return value=='+'||value=='-'||value=='*'||value=='/';
    }
    //计算的方法
    public int compute(int num1,int num2,int operation){
        int res=0;
        switch (operation){
            case '+':
                res=num1+num2;
                break;
            case '-':
                res=num2-num1;
                break;
            case '*':
                res=num1*num2;
                break;
            case '/':
                res=num2/num1;
                break;
                default:
                    break;
        }
        return res;
    }
    //可以返回当前栈顶的值，但不是真正的pop
    public int pick(){
        return stack[top];
    }

}
