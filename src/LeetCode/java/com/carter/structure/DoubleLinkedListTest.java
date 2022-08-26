package com.carter.structure;

public class DoubleLinkedListTest {
	public static void main(String[] args) {

	}
}

class DoubleHeroNode {
	public int				no;
	public String			name;
	public String			nickName;
	public DoubleHeroNode	next;
	public DoubleHeroNode	pre;

	//构造器
	public DoubleHeroNode(int no, String name, String nickName) {
		this.no = no;
		this.name = name;
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "DoubleHeroNode{" + "no=" + no + ", name='" + name + '\'' + ", nickName='" + nickName + '\'' + ", next=" + next + ", pre=" + pre + '}';
	}
}

/**
 * 双向链表的遍历，添加，修改，删除的操作思路
 * 遍历方式和单链表一样，可以向前，也可以向后
 * 添加（默认添加到双向链表的最后）
 * --先找到双向链表的最后这个节点
 * --temp.next=newHeroNode同时newHeroNode.pre = temp
 * 修改的思路和单向链表一样
 * 删除--因为是双向链表所以我们可以实现自我删除
 * --直接找到要删除的节点，比如temp
 * --temp.pre.next=temp.next和temp.next.pre=temp.pre
 */
class DoubleLinkedList {
	//先初始化一个头节点
	private DoubleHeroNode head = new DoubleHeroNode(0, "", "");

	//返回头节点
	public DoubleHeroNode getHead() {
		return head;
	}

	//显示链表
	public void list() {
		//1.判断链表是否为空
		if (head.next == null) {
			System.out.println("链表为空");
			return;
		}
		//2.因为是头节点，因此我们需要一个辅助变量来帮助我们完成遍历
		DoubleHeroNode temp = head.next;
		while (true) {
			//判断链表是不是到了最后
			if (temp == null) {
				break;
			}
			System.out.println(temp);
			temp = temp.next;
		}

	}

	//添加
	public void add(DoubleHeroNode heroNode) {
		DoubleHeroNode temp = head;
		while (true) {
			if (temp == null) {
				break;
			}
			temp = temp.next;
		}
		temp.next = heroNode;
		heroNode.pre = temp;
	}

	//修改一个节点的内容
	public void update(DoubleHeroNode heroNode) {
		if (head.next == null) {
			System.out.println("链表为空！");
			return;
		}
		DoubleHeroNode temp = head.next;
		boolean flag = false;
		while (true) {
			if (temp == null) {
				break;
			}
			if (temp.no == heroNode.no) {
				flag = true;
				break;
			}
			temp = temp.next;
			if (flag = true) {
				temp.nickName = heroNode.nickName;
				temp.name = heroNode.name;
			} else {
				System.out.printf("没有找到这个编号%d的节点" + heroNode.no);
			}
		}
	}

	//删除一个节点
	public void delete(int no) {
		if (head.next == null) {
			System.out.println("链表为空，不能删除");
			return;
		}
		DoubleHeroNode temp = head.next;//辅助节点
		boolean flag = false;
		while (true) {
			if (temp == null) {
				break;
			}
			if (temp.no == no) {
				flag = true;
				break;
			}
			temp = temp.next;
		}
		if (flag = true) {
			temp.pre.next = temp.next;
			//如果是最后的节点，就不需要执行下面这句话，否则就会报空指针异常
			if (temp.next != null) {
				temp.next.pre = temp.pre;
			}

		} else {
			System.out.printf("没有找到要删除的节点%d" + no);
		}
	}

}
