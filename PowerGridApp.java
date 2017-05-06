/*
 * Written by Justin Arnett (Group 5) Dec. 6, 2015
 */

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * The driver program that runs Prim's Algorithm on a graph.
 * Each line of the input text file consists of two vertices and a
 * weight for the edge.
 * 
 *      vertex1 vertex2 weight
 *      A B 14
 *      Robot Dolphin 42
 *      
 * 
 * @author Justin Arnett
 * @version December 6th, 2015
 */
public class PowerGridApp {
    
    

	public static void main(String[] args) throws EmptyHeapException {

	    // Asks for a filename and constructs a hashtable of vertices.
	    SimpleGraph graphObj = new SimpleGraph();
	    Hashtable vertices = GraphInput.LoadSimpleGraph(graphObj);

//	    BinaryHeap vertexHeap = buildHeapFromGraph(graphObj);
	    BinaryHeap vertexHeap = buildHeapWithVertices(vertices);
//	    printSolutionSet(graphObj.edgeList);
//	    printHeapArray(vertexHeap);
	    
	    LinkedList<Edge> minSpanTree = generateMinSpanTree(vertexHeap);
	    printSolutionSet(minSpanTree);
	    
	    
	} // end of main()
	
	
//	public static void printHeapArray(BinaryHeap h) {
//	    
//	    for (int i = 1; i < h.size + 1; i++) {
//	        System.out.print(((PrimHeapNode) h.elements[i]).getTheVertex().getName() + " ");
//	    }
//	    System.out.println();
//	}
	
	
	
	/**
	 * Prints the contents of a Linked List of Edges and the total weight of
	 * the edges.
	 * 
	 * @param theSolution The Linked list of edges.
	 */
	private static void printSolutionSet(LinkedList<Edge> theSolution) {
        Iterator<Edge> itr = theSolution.listIterator();
        Edge edge;
        Double total = new Double(0);
        
        System.out.println("+----------------------------------------+");
        System.out.println("|   Minimum Spanning Tree Solution Set   |");
        System.out.println("+----------------------------------------+");
        
        while (itr.hasNext()) {
            edge = itr.next();
            String v1 = edge.getFirstEndpoint().getName().toString();
            String v2 = edge.getSecondEndpoint().getName().toString();
            System.out.println("( " + v1 + " )---( " + v2 + " )  w(" + edge.getData() + ")");
            total = total + ((Double) edge.getData());
        }
        System.out.println();
        System.out.println("Total Weight: " + total);
        System.out.println("------------------------------------------");
        
    }



	/**
	 * Runs Prim's algorithm to generate a LinkedList of the edges that is
	 * comprised of the edges in the minimum spanning tree solution.
	 * 
	 * @param theHeap The minHeap with the content being the PrimHeapNodes of a graph.
	 * @return The list of edges in the minimum spanning tree solution.
	 * @throws EmptyHeapException 
	 */
    public static LinkedList<Edge> generateMinSpanTree(BinaryHeap theHeap) throws EmptyHeapException {
	    PrimHeapNode sourceNode;
	    LinkedList<Edge> minSpanTree = new LinkedList<Edge>();
	    
	    // Generate the starting node
	    if (!theHeap.isEmpty()) {
	        sourceNode = (PrimHeapNode) theHeap.deleteMin();
	        sourceNode.setLabel(-1.0);
	        System.out.println("\nStarting Vertex <-- ( " + sourceNode.getTheVertex().getName() + " )");
//	        printHeapArray(theHeap);
	        updateAdjacentNodes(sourceNode, theHeap);
//	        System.out.println("Source Node: " + sourceNode.getTheVertex().getName());
	    }
	    
	    // Keep taking min node and updating the adjacent nodes until heap is empty.
	    while (!theHeap.isEmpty()) {
	        sourceNode = (PrimHeapNode) theHeap.deleteMin();
	        sourceNode.setLabel(-1.0);
//	        System.out.println("Deleted Node: " + sourceNode.getTheVertex().getName());
//	        printHeapArray(theHeap);
	        
	        try {
	        minSpanTree.add(getNextSolutionEdge(sourceNode, theHeap));
//	        printHeapArray(theHeap);
	        } catch (DisconnectedGraphException e) {
	            System.err.println("Graph is disconnected or there was an error: " + e.getMessage());
	        }
	        
	    }
	    return minSpanTree;
	}
	
	
	/**
	 * Updates the label of nodes adjacent to theNode, then returns the edge
	 * of theNode's previous edge that updated its current label.
	 * 
	 * @param theNode The source node.
	 * @param theHeap The heap of PrimHeapNodes.
	 * @return The previous edge of theNode.
	 * @throws DisconnectedGraphException 
	 */
	public static Edge getNextSolutionEdge(PrimHeapNode theNode, BinaryHeap theHeap) throws DisconnectedGraphException {
	    	    
        if (!theNode.getLabel().equals(Double.POSITIVE_INFINITY)) {
            updateAdjacentNodes(theNode, theHeap);
            return theNode.getPreviousEdge();
            
        } else {
            throw new DisconnectedGraphException();
        }
	}  // end of getNextSolutionEdge()
	
	
	
//	/**
//	 * Builds the minHeap of PrimHeapNodes from the list of vertices in the
//	 * provided graph. The minHeap is sorted by the label of the PrimHeapNode.
//	 * 
//	 * @param theGraph The graph consisting of vertices and edges.
//	 * @return the minHeap of PrimHeapNodes.
//	 */
//	public static BinaryHeap buildHeapFromGraph(SimpleGraph theGraph) {
//	    
//	    int size = theGraph.numVertices();
//	    Iterator itr = theGraph.vertices();
//	    int i = 0;
//	    
//	    PrimHeapNode[] nodeList = new PrimHeapNode[size];
//	    PrimHeapNode node;
//	    
//	    // Builds a PrimHeapNode array with vertex nodes.
//	    while (itr.hasNext()) {
//	        Vertex tempV = (Vertex) itr.next();
//	        // Node = <vertex name, vertex obj, index location in heap>
//	        node = new PrimHeapNode(tempV, i+1);
//	        // Adds the PrimHeapNode to the vertex data field.
//	        tempV.setData(node);
//	        nodeList[i] = node;
//	        i++;
//	    }
//	    
//	    BinaryHeap heap = BinaryHeap.buildHeap(nodeList);
//	    return heap;
//	    
//	} // end of buildHeapWithVertices()
	
	
	/**
     * Builds the minHeap of PrimHeapNodes from the list of vertices in the
     * provided graph. The minHeap is sorted by the label of the PrimHeapNode.
     * 
     * @param theGraph The graph consisting of vertices and edges.
     * @return the minHeap of PrimHeapNodes.
     */
    public static BinaryHeap buildHeapWithVertices(Hashtable theVertices) {
        
        Set<String> keys = theVertices.keySet();
        int size = keys.size();
        Iterator itr = keys.iterator();
        int i = 0;
        
        PrimHeapNode[] nodeList = new PrimHeapNode[size];
        PrimHeapNode node;
        
        // Builds a PrimHeapNode array with vertex nodes.
        while (itr.hasNext()) {
            String tempS = (String) itr.next();
            // Node = <vertex obj, index location in heap>
            node = new PrimHeapNode((Vertex) theVertices.get(tempS), i+1);
            // Adds the PrimHeapNode to the vertex data field.
            ((Vertex) theVertices.get(tempS)).setData(node);
            nodeList[i] = node;
            i++;
        }
        
        BinaryHeap heap = BinaryHeap.buildHeap(nodeList);
        return heap;
        
    } // end of buildHeapWithVertices()

	
	
	/**
	 * Iterates through all the edges of a given PrimHeapNode. Updates adjacent PrimHeapNode's
	 * labels if the weight of the edge is less than the current label of the PrimHeapNode.
	 * 
	 * @param theSourceNode The starting node.
	 * @param theHeap The minHeap of PrimHeapNodes.
	 */
	public static void updateAdjacentNodes(PrimHeapNode theSourceNode, BinaryHeap theHeap) {
	    
	    // Makes an iterator of the linked list of edges of the vertex in the PrimHeapNode.
	    Iterator itr = theSourceNode.getTheVertex().incidentEdgeList.listIterator();
//	    theSourceNode.setLabel(-1.0);
	    
	    while (itr.hasNext()) {
	        // Get connecting vertex of the edge.
	        Edge currentEdge = (Edge) itr.next();
	        Vertex otherVertex = opposite(theSourceNode.getTheVertex(), currentEdge);
	        PrimHeapNode otherNode = (PrimHeapNode) otherVertex.getData();
	        
	        // If current edge weight is less than the node's weight label, update it.
	        if ((Double) currentEdge.getData() < otherNode.getLabel()) {
	            otherNode.setLabel((Double) currentEdge.getData());
	            otherNode.setPreviousEdge(currentEdge);
	            
	            // Update position of node in the heap.
	            int index = otherNode.getHeapPosition();
	            index = theHeap.updateHeapPosition(index);
	            otherNode.setHeapPosition(index);
	        }
	    }
	} // end of updateAdjacentNodes()
	
	
	
	/**
     * Given a vertex and an edge, if the vertex is one of the endpoints
     * of the edge, return the other endpoint of the edge.  Otherwise,
     * return null.
     * @param v  a vertex
     * @param e  an edge
     * @returns  the other endpoint of the edge (or null, if v is not an endpoint of e)
     */
    public static Vertex opposite(Vertex v, Edge e) {
        Vertex w;
        
        if (e.getFirstEndpoint() == v) {
            w= e.getSecondEndpoint();
        }
        else if (e.getSecondEndpoint() == v) {
            w = e.getFirstEndpoint();
        }
        else
            w = null;
        
        return w;
    } // end of opposite()

}
