package com.carter.structure;

import java.util.Scanner;

/**
 * hashtable散列表,需求：在不使用数据库的情况下，公司需要存储每一个雇员的信息，当查找雇员的id时，可以得到雇员的所有信息
 */
public class HashTableTest {
	public static void main(String[] args) {
		HashTab hashTab = new HashTab(7);
		String key = "";
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("add:添加雇员");
			System.out.println("list:显示雇员");
			System.out.println("exit:退出系统");
			System.out.println("find:查找雇员");
			key = scanner.next();
			switch (key) {
			case "add":
				System.out.println("输入id");
				int id = scanner.nextInt();
				System.out.println("输入name");
				String name = scanner.next();
				//创建雇员
				Emp emp = new Emp(id, name);
				hashTab.add(emp);
				break;
			case "list":
				hashTab.list();
				break;
			case "exit":
				scanner.close();
				break;
			case "find":
				System.out.println("输入id");
				int toSearch = scanner.nextInt();
				hashTab.findById(toSearch);
				break;
			default:
				break;
			}
		}

	}
}

//todo :表示一个雇员
class Emp {
	public int		id;
	public String	name;
	public Emp		next;	//默认为null

	public Emp(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Emp getNext() {
		return next;
	}

	public void setNext(Emp next) {
		this.next = next;
	}
}

//todo:创建EmpLinkedList,表示链表
class EmpLinkedList {
	//头指针，指向第一个Emp,因此我们这个链表的head，是直接指向第一个Emp
	private Emp head;

	//添加雇员到链表,假设id是自增长的
	public void add(Emp emp) {
		//如果添加第一个雇员
		if (head == null) {
			head = emp;
			return;
		}
		//如果不是第一个雇员，则使用一个辅助的指针，帮助定位到最后
		Emp currentEmp = head;
		while (true) {
			if (currentEmp.next == null) {//说明到聊表最后
				break;
			}
			currentEmp = currentEmp.next;
		}
		//退出时直接将emp加入链表
		currentEmp.next = emp;
	}

	//遍历链表的雇员信息
	public void list(int no) {
		if (head == null) {
			System.out.println("第" + no + "链表为空");
			return;
		}
		System.out.print("第" + no + "条，链表信息：");
		Emp currentEmp = head;//辅助指针
		while (true) {
			System.out.printf("=>id=%d name=%s\t", currentEmp.id, currentEmp.name);
			if (currentEmp.next == null) {
				break;
			}
			currentEmp = currentEmp.next;
		}
		System.out.println();
	}

	public Emp findById(int id) {
		if (head == null) {
			System.out.println("链表为空");
			return null;
		}
		//辅助指针
		Emp currentEmp = head;
		while (true) {
			if (currentEmp.id == id) {//找到
				break;
			}
			if (currentEmp.next == null) {
				currentEmp = null;
				break;
			}
			//如果上一个没有找到，就判断下一个
			currentEmp = currentEmp.next;
		}
		return currentEmp;
	}

}

//todo:创建hashTable，管理多条链表
class HashTab {
	private EmpLinkedList[]	empLinkedListArr;
	private int				size;				//表示有多少条链表

	//构造器
	public HashTab(int size) {
		this.size = size;
		//初始化empLinkedListArr
		empLinkedListArr = new EmpLinkedList[size];
		//todo:初始化每个链表
		for (int i = 0; i < size; i++) {
			empLinkedListArr[i] = new EmpLinkedList();
		}
	}

	//添加Emp的方法
	public void add(Emp emp) {
		//根据员工的id，得到该员工应当添加到哪条链表
		int empLinkedListNo = hashFunction(emp.id);
		//将emp添加到对应的链表中
		empLinkedListArr[empLinkedListNo].add(emp);
	}

	//遍历所有的链表也就是遍历hashTable
	public void list() {
		for (int i = 0; i < size; i++) {
			empLinkedListArr[i].list(i);
		}
	}

	//todo：编写散列函数，使用一个简单取模法
	public int hashFunction(int id) {
		return id % size;
	}

	//依据输入的id，查找雇员
	public void findById(int id) {
		int empLinkedListNo = hashFunction(id);
		Emp result = empLinkedListArr[empLinkedListNo].findById(id);
		System.out.println(result.getName());
	}
}