import java.io.*;

class QueueElement {
	Object element;
	QueueElement next;

	QueueElement() {
		element = null;
		next = null;
	}
}

class Queue {
	
	private QueueElement front;
	private QueueElement rear;
	
	public Queue() {
		front = null;
		rear = null;
	}
	
	public boolean empty() {
		return (front == null);
	}
	
	public Object front() {
		return front.element;
	}
	
	public void enqueue(Object obj) {
		QueueElement nov = new QueueElement();
		nov.element = obj;
		if(front == null) front = nov;
		else rear.next = nov;
		rear = nov;
	}
	
	public void dequeue() {
		if(front != null) front = front.next;
	}
}


class BSTNode {
	int val;
	int key;
	int leftVal;
	int rightVal;
	BSTNode left;
	BSTNode right;
	
	public BSTNode(int k, int val, int leftVal, int rightVal) {
		this.key = k;
		this.val = val;
		this.leftVal = leftVal;
		this.rightVal = rightVal;
		left = null;
		right = null;
	}
}

class BSTree {
	BSTNode root;
	
	public BSTree() {
		makenull();
	}
	
	void makenull() {
		root = null;
	}
	
	public boolean insert(BSTNode node) {
		if(root == null) root = node;
		else {
			if(!vstaviKotSina(root, node) && !vstaviKotKoren(node))
				return false;
		}
		return true;
	}
	
	private boolean vstaviKotSina(BSTNode current, BSTNode node) {
		if(current == null) return false;
		else {
			if(node.key == current.leftVal) {
				current.left = node;
				return true;
			}
			else if(node.key == current.rightVal) {
				current.right = node;
				return true;
			}
			else {
				return vstaviKotSina(current.left, node) | vstaviKotSina(current.right, node);
			}
		}
	}
	
	private boolean vstaviKotKoren(BSTNode node) {
		if(node.leftVal == root.key) {
			node.left = root;
			root = node;
			return true;
		}
		else if(node.rightVal == root.key) {
			node.right = root;
			root = node;
			return true;
		}
		else return false;
	}
}

public class Naloga8 {
	private static int sirina;
	
	public static void main(String[] args) throws IOException {
		BSTree tree = new BSTree();
		
		//in
		int n = 0;
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		n = Integer.parseInt(br.readLine().trim());
		Queue queue = new Queue();
		for(int i = 0; i < n; i++) {
			String[] currentLine = br.readLine().split(",");
			BSTNode node = new BSTNode(Integer.parseInt(currentLine[0]), Integer.parseInt(currentLine[1]),
					   				   Integer.parseInt(currentLine[2]), Integer.parseInt(currentLine[3]));
			queue.enqueue(node);
		}
		
		//gradnja drevesa
		while(!queue.empty()) {
			BSTNode currentNode = (BSTNode)queue.front();
			queue.dequeue();
			if(!tree.insert(currentNode)) queue.enqueue(currentNode);
		}
		br.close();
		
		//matrix
		int[][] mx = new int[n][n];
		sirina = 0;
		ustvariMatriko(mx, tree.root, 0);
		
		//out
		izpisi(mx, args[1], n);
	}
	
	public static void ustvariMatriko(int[][] mx, BSTNode node, int visina) {
		if(node == null) return ;
		else {
			ustvariMatriko(mx, node.left, visina + 1);
			mx[visina][sirina] = node.val;
			sirina++;
			ustvariMatriko(mx, node.right, visina + 1);
		}
	}
	
	public static void izpisi(int[][] mx, String outFile, int size) throws IOException {
		PrintWriter p = new PrintWriter(new FileWriter(outFile));
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(mx[i][j] != 0) {
					p.println(mx[i][j] + "," + j + "," + i);
				}
			}
		}
		p.close();
	}
}
