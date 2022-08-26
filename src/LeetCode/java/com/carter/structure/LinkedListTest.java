package com.carter.structure;

/**
 链表是以节点的方式来存储
 每个节点包含data域，next域：指向下一个节点
 链表的各个节点不一定是连续存储
 链表分为带头节点的链表和没有头节点的链表，依据实际的需求来确定

 使用带head头的单向链表实现水浒英雄排行榜管理
 --完成对英雄人物的增删改查操作

 head节点：
 --1.head不存放具体的数据
 --2.作用就是表示单链表头
 --3.next域指向下一个节点

 dataNode节点：
 --1.存放数据
 --2.next指向下一个节点
 --3.判断链表是不是结束，则是以next是否为空来判断

 添加（创建）
 1.先创建一个head头节点，作用就是表示单链表的头
 2.后面我们每添加一个节点，就直接加入到链表的最后

 删除
 1.我们先找需要删除的前一个节点temp
 2.只需要temp.next=temp.next.next
 3.被删除的节点将不会有其他引用指向，会被垃圾回收机制回收
 */

public class LinkedListTest {
	public static void main(String[] args) {
		//进行测试
		HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
		HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
		HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
		HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
		//加入
		SingleLinkedList singleLinkedList = new SingleLinkedList();
		singleLinkedList.addByOrder(hero1);
		singleLinkedList.addByOrder(hero3);
		singleLinkedList.addByOrder(hero2);
		singleLinkedList.addByOrder(hero4);

		HeroNode heroNode = new HeroNode(4, "xiolu", "玉麒麟--");
		HeroNode heroNode1 = new HeroNode(3, "xiolu", "玉麒麟--");
		singleLinkedList.update(heroNode);
		singleLinkedList.update(heroNode1);
		singleLinkedList.delNode(4);
		singleLinkedList.list();
	}
}

class HeroNode {
	public int		no;
	public String	name;
	public String	nickName;
	public HeroNode	next;		//指向下一个节点
	//构造器

	public HeroNode(int heroNo, String heroName, String nickName) {
		this.name = heroName;
		this.nickName = nickName;
		this.no = heroNo;
	}

	@Override
	public String toString() {
		return "HeroNode{" + "no=" + no + ", name='" + name + '\'' + ", nickName='" + nickName + '}';
	}
}

class SingleLinkedList {
	//先初始化一个头节点,头节点不能动
	private HeroNode head = new HeroNode(0, "", "");

	//添加节点到单向链表中
	public void add(HeroNode heroNode) {
		HeroNode temp = head;
		//遍历链表，找到最后
		while (true) {
			//找到了
			if (temp.next == null) {
				break;
			}
			//没有找到
			temp = temp.next;
		}
		//当退出while循环，temp就指向了链表的最后
		//将最后这个节点的next指向新的节点
		temp.next = heroNode;
	}

	//第二种方式，需要添加到指定的位置
	public void addByOrder(HeroNode heroNode) {
		/**
		 * 因为头节点不能动，所以我们仍然需要通过一个辅助指针（变量）来帮助找到添加的位置
		 * 因为单链表，因此我们找的temp是位于添加位置的前一个节点，否则插入无效
		 */
		HeroNode temp = head;
		boolean flag = false;//flag标志添加的编号是否存在，默认为false
		while (true) {
			if (temp.next == null) {
				break;
			}
			if (temp.next.no > heroNode.no) {
				//位置已经找到，就在temp的后面插入
				break;
			} else if (temp.next.no == heroNode.no) {
				//说明希望添加的heroNode的编号已经存在
				flag = true;
				break;
			}
			temp = temp.next;//后移
		}
		//退出while循环，需要判断flag的值
		if (flag) {
			System.out.printf("准备插入放入英雄编号%d已经存在，不能添加\n", heroNode.no);
		} else {
			//插入到链表中
			heroNode.next = temp.next;
			temp.next = heroNode;
		}

	}

	//显示链表，需要辅助变量，以及遍历
	public void list() {
		//判断链表是否为空
		if (head.next == null) {
			System.out.println("单向链表为空！");
		}
		//因为头节点不能动，因此需要一个辅助变量来遍历
		HeroNode temp = head.next;
		while (true) {
			//判断是否最后
			if (temp == null) {
				break;
			}
			//输出节点信息
			System.out.println(temp);
			//将temp节点后移
			temp = temp.next;
		}

	}

	//修改节点信息(根据编号来修改，但是编号不能够修改)
	public void update(HeroNode newHeroNode) {
		if (head.next == null) {
			System.out.println("链表为空！");
		}
		//找到需要修改的节点，根据no编号
		//定义一个辅助变量
		HeroNode temp = head.next;
		boolean flag = false;
		while (true) {
			if (temp == null) {
				break;
			}

			if (temp.next.no == newHeroNode.no) {
				flag = true;
				break;
			}
			temp = temp.next;
		}
		if (true) {
			temp.next.name = newHeroNode.name;
			temp.next.nickName = newHeroNode.nickName;
		} else {
			System.out.printf("没有找到编号%d的节点！", newHeroNode.no);
		}
	}

	//删除节点
	public void delNode(int no) {
		HeroNode temp = head;
		boolean flag = false;
		while (true) {
			if (temp.next == null) {
				break;
			}
			if (temp.next.no == no) {
				flag = true;
				break;
			}
			temp = temp.next;//temp后移才能实现遍历
		}
		//删除成立的条件
		if (flag) {
			temp.next = temp.next.next;
		} else {
			System.out.printf("要删除的节点%d没有找到!", no);
		}
	}

	//获取到单链表的节点个数
	public static int getLength(HeroNode head) {
		if (head.next == null) { return 0; }
		int length = 0;
		//定义一个辅助的变量
		HeroNode curHeroNode = head.next;
		while (curHeroNode != null) {
			length++;
			curHeroNode = curHeroNode.next;
		}
		return length;
	}

	//查找单链表的倒数第K个节点【新浪】
	//1.编写一个方法，接收head节点，同时接受一个index
	//2.index表示倒数第index个节点
	//3.先将链表从头到尾遍历，得到链表长度length，从而我们正向得到length-index
	public static HeroNode getLastIndex(HeroNode head, int index) {
		if (head.next == null) { return null; }
		//第一次遍历得到链表的长度
		int size = getLength(head);
		//第二次需要遍历到size-index，就是倒数K个节点
		if (index <= 0 || index > size) { return null; }
		//定义辅助变量
		HeroNode current = head.next;
		for (int i = 0; i < size - index; i++) {
			current = current.next;
		}
		return current;
	}

	//单链表的反转【腾讯】
	//1.先定义一个节点reverseHead = new HeroNode();
	//2.从头到尾遍历原来的链表，每遍历一个就放入新链表的最前端
	//3.原来的链表的head.next = reverseHead.next
	public static void reverseNode(HeroNode head) {
		if (head.next == null || head.next.next == null) { return; }
		//定义一个辅助变量，帮助我们遍历原来的链表
		HeroNode current = head.next;
		HeroNode next = null;//指向当前节点的下一个节点
		HeroNode reverseHead = new HeroNode(0, "", "");
		while (current != null) {
			next = current.next;//先暂时保存当前节点的下一个节点
			current.next = reverseHead.next;//将current的下一个节点指向新链表的最前端
			reverseHead.next = current;
			current = next;//让current后移
		}
		//将head.next指向reverseHead.next
		head.next = reverseHead.next;
	}

}
