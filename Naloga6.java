import java.io.*;

class BSTNode {
	Comparable key;
	BSTNode left;
	BSTNode right;
	
	public BSTNode(Comparable k) {
		key = k;
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
	
	public int height(BSTNode node) {
		if(node == null) return 0;
		else {
			int left = 1 + height(node.left);
			int right = 1 + height(node.right);
			return (left > right) ? left : right;
		}
	}
}

public class Naloga6 {
	
	public static BSTNode zgradiDrevo(String[] input, int low, int high) {
		BSTNode root;
		int or = -1; 
		int and = -1;
		int stOklepajev = 0;
		for(int i = low; i <= high; i++) {
			if(input[i].contains("(")) 
				stOklepajev += input[i].length() - input[i].replace("(", "").length();
			if(input[i].contains(")")) 
				stOklepajev -= input[i].length() - input[i].replace(")", "").length();
			if(input[i].equals("OR") && stOklepajev == 0) or = i;
			if(input[i].equals("AND") && stOklepajev == 0) and = i;
		}
		if(or != -1) {
			root = new BSTNode(input[or]);
			root.left = zgradiDrevo(input, low, or - 1);
			root.right = zgradiDrevo(input, or + 1, high);
		}
		else if(and != -1) {
			root = new BSTNode(input[and]);
			root.left = zgradiDrevo(input, low, and - 1);
			root.right = zgradiDrevo(input, and + 1, high);
		}
		else {
			if(input[low].equals("NOT")) {
				root = new BSTNode("NOT");
				root.left = zgradiDrevo(input, low + 1, high);
			}
			else if(input[low].contains("(")) {
				input[low] = input[low].substring(1);
				input[high] = input[high].substring(0, input[high].length() - 1);
				root = zgradiDrevo(input, low, high);
			}
			//Robni Pogoj
			else {
				root = new BSTNode(input[low]);
			}
		}
		return root;
	}
	
	public static void main(String[] args) throws IOException {
		//in
		String[] input = dobiInput(args[0]);
		
		//main
		BSTree tree = new BSTree();
		tree.root = zgradiDrevo(input, 0, input.length - 1);
		
		//out
		if(tree.root != null) izpisiDrevo(tree, args[1]);
	}
	
	public static String[] dobiInput(String inFile) throws IOException {
		String input = "";
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		input = br.readLine();
		br.close();
		
		String[] splitInput = input.trim().split("\\s+");
		return splitInput;
	}
	
	public static void izpisiDrevo(BSTree tree, String outFile) throws IOException {
		PrintWriter p = new PrintWriter(new FileWriter(outFile));
		String izpis = izpisiPreorder(tree.root);
		izpis = izpis.substring(0, izpis.length() - 1);
		p.println(izpis);
		p.println(tree.height(tree.root));
		p.close();
	}
	
	public static String izpisiPreorder(BSTNode node) {
		if(node == null) return "";
		else return (String)node.key + "," + izpisiPreorder(node.left) + izpisiPreorder(node.right);
	}
}