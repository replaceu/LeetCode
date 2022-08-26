package com.carter.structure;

/**
 * 如果以二叉排序树来存储数据，那么对数据的增删改查的效率都会提高
 */
public class BinaryTreeTest {
	public static void main(String[] args) {
		BinaryTree binaryTree = new BinaryTree();
		//todo:创建所需要的节点
		ManNode root = new ManNode(1, "Carter");
		ManNode node2 = new ManNode(2, "Peter");
		ManNode node3 = new ManNode(3, "Mary");
		ManNode node4 = new ManNode(4, "Bob");
		ManNode node5 = new ManNode(5, "Allen");

		//todo:手动创建该二叉树
		root.setLeft(node2);
		root.setRight(node3);
		node3.setRight(node4);
		node3.setLeft(node5);
		binaryTree.setRoot(root);
		//binaryTree.postOrder();
		ManNode result = binaryTree.postSearch(5);
		System.out.println(result.toString());
	}
}

//todo:定义二叉树
class BinaryTree {
	private ManNode root;

	public void setRoot(ManNode root) {
		this.root = root;
	}

	public void preOrder() {
		if (this.root != null) {
			this.root.preOrder();
		} else {
			System.out.println("二叉树为空，无法遍历！");
		}
	}

	public ManNode preSearch(int no) {
		if (this.root != null) {
			return root.preSearch(no);
		} else {
			return null;
		}
	}

	public void infixOrder() {
		if (this.root != null) {
			this.root.infixOrder();
		} else {
			System.out.println("二叉树为空，无法遍历！");
		}
	}

	public ManNode infixSearch(int no) {
		if (this.root != null) {
			return root.infixSearch(no);
		} else {
			return null;
		}
	}

	public void postOrder() {
		if (this.root != null) {
			this.root.postOrder();
		} else {
			System.out.println("二叉树为空，无法遍历！");
		}
	}

	public ManNode postSearch(int no) {
		if (this.root != null) {
			return root.postSearch(no);
		} else {
			return null;
		}
	}

	//删除节点的方法
	public void delNode(int no) {
		if (root != null) {
			if (root.getNo() == no) {
				root = null;
			} else {
				//todo:递归删除
				root.delNode(no);
			}
		}
	}

}

//todo：先创建HeroNode节点 
class ManNode {
	private int		no;
	private String	name;
	private ManNode	left;
	private ManNode	right;

	public ManNode(int no, String name) {
		this.no = no;
		this.name = name;
	}

	public ManNode() {
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public ManNode getLeft() {
		return left;
	}

	public void setLeft(ManNode left) {
		this.left = left;
	}

	public ManNode getRight() {
		return right;
	}

	public void setRight(ManNode right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "ManNode{" + "no=" + no + ", name='" + name + '\'' + '}';
	}

	//编写前序遍历的方法
	public void preOrder() {
		System.out.println(this);//先输出父节点
		//递归向左子树前序遍历
		if (this.left != null) {
			this.left.preOrder();
		}
		if (this.right != null) {
			this.right.preOrder();
		}
	}

	//编写中序遍历的方法
	public void infixOrder() {
		//递归向左子树的中序遍历
		if (this.left != null) {
			this.left.infixOrder();
		}
		//输出当前节点
		System.out.println(this);
		//递归向右子树的中序遍历
		if (this.right != null) {
			this.right.infixOrder();
		}
	}

	//后序遍历的方法
	public void postOrder() {
		if (this.left != null) {
			this.left.postOrder();
		}
		if (this.right != null) {
			this.right.postOrder();
		}

		System.out.println(this);
	}

	//前序查找指定序号的方法
	public ManNode preSearch(int no) {
		ManNode result = null;
		if (no == this.no) {
			result = this;
		} else {
			if (this.left != null) {
				result = this.left.preSearch(no);
			}
			if (result != null) { return result; }
			if (this.right != null) {
				result = this.right.preSearch(no);
			}
		}
		return result;
	}

	//中序查找指定节点的方法
	public ManNode infixSearch(int no) {
		ManNode result = null;
		if (this.left != null) {
			result = this.left.infixSearch(no);
		}
		if (result != null) { return result; }
		if (this.no == no) { return this; }
		if (this.right != null) {
			result = this.right.infixSearch(no);
		}

		return result;
	}

	//后序遍历查找指定节点的方法
	public ManNode postSearch(int no) {
		ManNode result = null;
		if (this.left != null) {
			result = this.left.postSearch(no);
		}
		if (result != null) { return result; }
		if (this.right != null) {
			result = this.right.postSearch(no);
		}
		if (result != null) { return result; }
		if (this.no == no) { return this; }
		return result;

	}

	//递归删除节点
	public void delNode(int no) {
		//如果当前节点的左子节点不为空，并且左子节点就是要删除的节点，就将this.left=null,并且就返回（结束的时候递归删除）;
		if (this.left != null && this.left.no == no) { return; }
		if (this.right != null && this.right.no == no) { return; }
		//需要向左子树进行递归删除
		if (this.left != null) {
			this.left.delNode(no);
		}
		if (this.right != null) {
			this.right.delNode(no);
		}
	}
}