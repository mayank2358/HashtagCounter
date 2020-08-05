import java.io.*;
import java.util.*;

/**
 * 
 * @author Mayank Sharma
 *
 */
public class HashTagCounter {
	/**
	 * 
	 * @author Mayank Sharma
	 *
	 */
	public class Node {
		String hashtag;
		int key;
		int f_degree;
		boolean childCut;
		Node f_child;									
		Node left;									
		Node parent;									
		Node right;									
		/**
		 * default constructor
		 */
		public Node() {
		}
		/**
		 * Constructor
		 * @param key
		 */
		public Node(int key) {
			right = this;
			left = this;
			this.key = key;
		}
		/**
		 * Constructor
		 * @param key
		 * @param hashtag
		 */
		public Node(int key, String hashtag) {
			right = this;
			left = this;
			this.key = key;
			this.hashtag = hashtag;
		}
		/**
		 * Returns key of the Node called.
		 * @return int
		 */
		public final int getKey() {
			return key;
		}
		/**
		 * Returns the hashtag of the Node called.
		 * @return String
		 */
		public final String getHashtag() {
			return hashtag;
		}
	}
	public class FibonacciHeap {
		/** Maximum node in Fibonacci heap. */
		private Node maxNode;
		/** Number of nodes in Fibonacci heap. */
		private int nodeCount;

		/**
		 * Returns TRUE if the heap is empty
		 * and FALSE otherwise.
		 * @return
		 */
		public boolean isHeapEmpty() {
			if(maxNode == null)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}

		/**
		 * Inserts nodes into Fibonacci heap.
		 * @param newNode
		 * @param key
		 */
		public void insert_node(Node newNode, int key) {
			newNode.key = key;
			//Heap is not empty
			if(maxNode != null) {                  
				newNode.left = maxNode;        
				newNode.right = maxNode.right;
				maxNode.right = newNode;
				newNode.right.left = newNode;
				//Setting the maxNode
				if(key > maxNode.key) {             
					maxNode = newNode;
				}
			}
			//Heap is empty.
			else {                                  
				maxNode = newNode;
			}
			//Incrementing the node count.
			nodeCount++;                           
		}

		/**
		 * Increases the key of the given node
		 * to the value passed.
		 * @param current
		 * @param newKey
		 * @return Node
		 */
		public Node f_incr_key(Node current, int newKey) {
			Node parent=null;
			current.key = newKey;                    
			parent = current.parent;
			//if the current node key is greater than the parent 
			//call the cut and cascade to maintain  
			//Max Fibonacci heap properties
			
			if((parent != null) && (current.key > parent.key)) { 
				cut(current, parent);
				cascadeCut(parent);
			}
			//Setting the maxNode
			if(current.key > maxNode.key) {          
				maxNode = current;
			}
			return current;
		}

		/**
		 * This removes and returns the maxNode from the
		 * Max Fibonacci heap
		 * @return Node
		 */
		public Node f_removeMax() {
			Node nodeToDelete = maxNode;
			if(nodeToDelete != null) {
				int  childCount = nodeToDelete.f_degree;
				Node child = nodeToDelete.f_child;
				Node tempRight;
				//child node to delete
				while(childCount > 0) {
					tempRight = child.right;
					//remove child from the list
					child.left.right = child.right;
					child.right.left = child.left;
					//add the child to the root 
					child.left = maxNode;
					child.right = maxNode.right;
					maxNode.right = child;
					child.right.left = child;
					//set the parent of child to null
					child.parent = null;
					child = tempRight;
					childCount--;
				}
				//remove the nodeToDelete from the root list
				nodeToDelete.left.right = nodeToDelete.right;
				nodeToDelete.right.left = nodeToDelete.left;
				if(nodeToDelete == nodeToDelete.right) {
					maxNode = null;
				}
				else {
					maxNode = nodeToDelete.right;
					pairwiseCombine();
				}
				//decrement the size of the heap
				nodeCount--;
			}
			//removed maxnode is returned
			return nodeToDelete;
		}

		/**
		 * child is removed from the child list of parent
		 * @param child
		 * @param parent
		 */
		protected void cut(Node child, Node parent) {
			//remove the node from the child list of parent
			child.left.right = child.right;
			child.right.left = child.left;
			parent.f_degree--;
			//set the child of parent to appropriate node
			if(parent.f_child == child) {
				parent.f_child = child.right;
			}
			if(parent.f_degree == 0) {
				parent.f_child = null;
			}
			//add the child to the root list of heap
			child.left = maxNode;
			child.right = maxNode.right;
			maxNode.right = child;
			child.right.left = child;
			child.parent = null;
			child.childCut = false;
		}

		/**
		 * Cuts the child from its parent till a parent with
		 * childCut value FALSE is encountered.
		 * @param child
		 */
		protected void cascadeCut(Node child) {
			Node parent = child.parent;
			if(parent != null) {
				if(!child.childCut) {
					child.childCut = true;
				}
				else {
					cut(child, parent);
					cascadeCut(parent);
				}
			}
		}

		/**
		 * Combines the trees in the heap by joining trees of equal degree
		 * until there are no more trees of equal degree in the root list.
		 */
		protected void pairwiseCombine() {
			int arraySize = nodeCount + 1;
			Node[] roots = new Node[arraySize];
			for(int i = 0; i < arraySize; i++) {
				roots[i] = null;
			}
			// Find the number of root nodes.
			int numRoots = 0;
			Node x = maxNode;

			if(x != null) {
				numRoots++;
				x = x.left;

				while(x != maxNode) {
					numRoots++;
					x = x.left;
				}
			}
			while(numRoots > 0) {
				int d = x.f_degree;
				Node next = x.left;
				while(roots[d] != null) { //if root node exists with the same degree
					Node y = roots[d];
					if(x.key < y.key) {
						Node temp = y;
						y = x;
						x = temp;
					}
					
					// remove y from root list of heap
					y.left.right = y.right;
					y.right.left = y.left;
					y.parent = x;
					if(x.f_child == null) {
						x.f_child = y;
						y.right = y;
						y.left = y;
					}
					else {
						y.left = x.f_child;
						y.right = x.f_child.right;
						x.f_child.right = y;
						y.right.left = y;
					}
					//Increment the degree of the x
					x.f_degree++;
					//Set the childCut of y to FALSE
					y.childCut = false;

					roots[d] = null;
					d++;
				}
				roots[d] = x;
				x = next;
				numRoots--;
			}
			maxNode = null;
			// Reconstruct the root list from the array entries in roots[].
			for(int i = 0; i < arraySize; i++) {
				if(roots[i] != null) {           
					if(maxNode != null) {
						roots[i].left.right = roots[i].right;
						roots[i].right.left = roots[i].left;
						roots[i].left = maxNode;
						roots[i].right = maxNode.right;
						maxNode.right = roots[i];
						roots[i].right.left = roots[i];
						//Setting the maxNode
						if(roots[i].key > maxNode.key) {           
							maxNode = roots[i];
						}
					}
					else {
						maxNode = roots[i];
					}
				}
			}
		}
	}

	//Program starts from here...
	//File output = new File("output_file.txt");   
	//ArrayList to store removed Nodes
	ArrayList<Node> removedNodes = new ArrayList<Node>();
	//Max Fibonacci heap
	FibonacciHeap fibonacciHeap = new FibonacciHeap();
	//Hash table
	Hashtable<String, Node> hashtable = new Hashtable<String, Node>();

	/**
	 * Reads each line of the input file and processes accordingly.
	 * @param inputFile
	 */
	void readInput(String inputFile, String outputFile) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				//Input line starts with #
				if(inputLine.startsWith("#")){             
					//Extract the hashtag
					String hashtag=inputLine.split(" ")[0].substring(1, inputLine.split(" ")[0].length());
					//Extract the frequency of hashtag
					int frequency=Integer.parseInt(inputLine.split(" ")[1]);
					//If hashtag was not encountered so far, add a new node to the
					//Fibonacci heap
					if(!hashtable.containsKey(hashtag)){
						Node newNode=new Node(frequency, hashtag);
						fibonacciHeap.insert_node(newNode, frequency);
						hashtable.put(hashtag,newNode);
					}
					//If the node already exists, increment its frequency.
					else{
						int newFrequency=hashtable.get(hashtag).getKey()+frequency;
						Node x=fibonacciHeap.f_incr_key(hashtable.get(hashtag),newFrequency);
						hashtable.remove(hashtag);
						hashtable.put(hashtag, x);
					}       
				}
				else {
					//If line is STOP, terminate
					if(inputLine.equalsIgnoreCase("stop")){
						return;
					}
					else{  
						//If query, generate the fibonacci heap and call f_removeMax()
						process(inputLine,outputFile);
					}
				}
			}
		}
		catch (FileNotFoundException e) {          
			
		}
		catch (Exception e) {
		
		}
	}

	/**
	 * Writes the n most popular hashtags to the output file.
	 * @param outputFile
	 * @param outputLine
	 */
	void writeFile(File outputFile, String outputLine){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(outputFile, true));
			bw.write(outputLine);
			bw.newLine();
			bw.flush();
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{                      
			if (bw != null)
			{
				try {
					bw.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
	
	void writeFile(String outputLine){
		try {
		
	                System.out.println(outputLine);
	            
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * It generates the Max Fibonacci heap 
	 * calls f_removeMax() for
	 * query number of times.
	 * @param queryString
	 */
	void process(String queryString, String OF){
		int query=0;
		try{
			query=Integer.parseInt(queryString) ;
		}
		catch(NumberFormatException e){
		}
		String outputLine="";
		while(query>0){		
			//remove max node from fibonacci heap
			Node maxNode=fibonacciHeap.f_removeMax();
			Node removedNode=new Node(maxNode.getKey(),maxNode.getHashtag());
			removedNodes.add(removedNode);
			String maxNodeHashtag = removedNode.getHashtag();
			//remove the hashtable entry corresponding to the maxNode
			hashtable.remove(maxNodeHashtag);
			//Append to the outline string
			outputLine=outputLine+maxNodeHashtag+",";
			query--;
		}
		//write to the output file
		
		if (OF.equals("outincmd"))
		{
			writeFile(outputLine.substring(0, outputLine.length()-1));
		}
		else
		{
		File output = new File(OF);  
		writeFile(output,outputLine.substring(0, outputLine.length()-1));
		}

		//Add back the removed nodes to the Max Fibonacci heap
		for(int remNodeCount=0;remNodeCount<removedNodes.size();remNodeCount++){
			fibonacciHeap.insert_node(removedNodes.get(remNodeCount), removedNodes.get(remNodeCount).getKey());
			hashtable.put(removedNodes.get(remNodeCount).getHashtag(), removedNodes.get(remNodeCount));
		}
		//clear the removeNodes array list
		removedNodes.clear();
	}
	static HashTagCounter p=new HashTagCounter();
	public static void main(String[] args) {
		//input file name is saved in args[0]
		int count = args.length;
		if (count == 2)
		{
		p.readInput(args[0],args[1]);
		}
		else if (count == 1)
		{
		p.readInput(args[0],"outincmd");
		}
		else
		{
			System.out.println("Input entered in a wrong way");
		}
	}
}
