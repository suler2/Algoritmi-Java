import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class Node{	
	int id;
	//marks for the algorithm
	//------------------------------------
	boolean marked = false;
	Edge augmEdge = null; //the edge over which we brought the flow increase
	int incFlow = -1; //-1 means a potentially infinite flow
	//------------------------------------
	ArrayList<Edge> inEdges;
	ArrayList<Edge> outEdges;
	
	public Node(int i) {
		id = i;
		inEdges = new ArrayList<Edge>();
		outEdges = new ArrayList<Edge>();
	}
}

class Edge{
	int startID; 
	int endID;
	int capacity; 
	int currFlow;
	
	public Edge(int fromNode, int toNode, int capacity2) {
		startID = fromNode;
		endID = toNode;
		capacity = capacity2;
		currFlow = 0;
	}
}

class Network{
	Node[] nodes;
	
	/**
	 * Create a new network with n nodes (0..n-1).
	 * @param n the size of the network.
	 */
	public Network(int n){
		nodes = new Node[n];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i]= new Node(i);
		}
	}
	/**
	 * Add a connection to the network, with all the corresponding in and out edges.
	 * @param fromNode
	 * @param toNode
	 * @param capacity
	 */
	public void addConnection(int fromNode, int toNode, int capacity){
		Edge e = new Edge(fromNode, toNode, capacity);
		nodes[fromNode].outEdges.add(e);
		nodes[toNode].inEdges.add(e);
	}

	/**
	 * Reset all the marks of the algorithm, before the start of a new iteration.
	 */
	public void resetMarks(){
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].marked = false;
			nodes[i].augmEdge = null;
			nodes[i].incFlow = -1;
		}
	}
}

public class Izziv9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Edge> listPovezav = new ArrayList<Edge>();
        while(sc.hasNext()) {
            Edge nov = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
            listPovezav.add(nov);
        }

        ArrayList<Node> listTock = new ArrayList<Node>();
        for(int i = 0; i < n; i++) {
            listTock.add(new Node(i));
            Iterator j = listPovezav.iterator();
            while(j.hasNext()) {
                if(listPovezav.startID == listTock.id)
                    listTock.inEdges.add(listPovezav);
                if(listPovezav.endID == listTock.id)
                    listTock.outEdges.add(listPovezav);
            }
            j.close();
        }
        sc.close();


    }
}