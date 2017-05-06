
/**
 * A heap node that is tailored for prim's algorithm.
 * 
 * @author Justin Arnett
 * @version December 6, 2015
 */
public class PrimHeapNode implements Comparable{
    
    Double label;
    Vertex theVertex;
    int heapPosition;
    Edge previousEdge;
    
    public PrimHeapNode (Vertex v, int pos) {
        label = Double.POSITIVE_INFINITY;
        theVertex = v;
        heapPosition = pos;
        previousEdge = null;
    }

    public Double getLabel() {
        return label;
    }

    public void setLabel(Double label) {
        this.label = label;
    }

    public Vertex getTheVertex() {
        return theVertex;
    }

    public void setTheVertex(Vertex theVertex) {
        this.theVertex = theVertex;
    }

    public int getHeapPosition() {
        return heapPosition;
    }

    public void setHeapPosition(int heapPosition) {
        this.heapPosition = heapPosition;
    }

    public Edge getPreviousEdge() {
        return previousEdge;
    }

    public void setPreviousEdge(Edge previousEdge) {
        this.previousEdge = previousEdge;
    }

    @Override
    public int compareTo(Object arg) {
        if (arg instanceof PrimHeapNode) {
            PrimHeapNode other = (PrimHeapNode) arg;
            return this.getLabel().compareTo(other.getLabel());
        }
        return 0;
    }
    
    
    

}
