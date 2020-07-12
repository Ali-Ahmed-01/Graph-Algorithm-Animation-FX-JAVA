package Algorithms;

import BSTgraph_.Edge;
import BSTgraph_.Tree;
import BSTgraph_.vertex;
import java.util.ArrayList;
import GUI.mainC;
import javax.swing.JOptionPane;

public final class UCS_Algorithm {

    // an ArrayList that help to contain visited vertex , if a vertex has visited then that vertex add to this arrayList
    public ArrayList<vertex> _vertexHASvisited;
    // a periorityQueue , help in algo Execution
    private periorityQueue_UCS periorityQueue;

    vertex startingPoint, Endingpoint;    // starting point and ending point 
    // path if found
    public String path;
    public int Cost;

    public UCS_Algorithm() {
        init();
    }

    public void init() {
        // arrayList init
        _vertexHASvisited = new ArrayList<>();
        this.periorityQueue = new periorityQueue_UCS();
        Algorithm_init();
    }

    public void Algorithm_init() {
        // "A" is starting point as graph 
        this.startingPoint = Tree.getBST().getNode(Tree.getBST().root.data.nodename);
        this.Endingpoint = Tree.getBST().getNode(mainC.goal);
        // algorithm execution

        if (Endingpoint != null) {
            Algorithm();
        } else {
            JOptionPane.showMessageDialog(null, "", "Goal is not define", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void enqueueAllchild(VertexWithroutePath_UCS temporaryNode, int markedValue) {
        for (Edge obj : temporaryNode.vertex.getNeighbours()) {
            if (!isVisited(obj.chid)) {
                this.periorityQueue.enqueue(new VertexWithroutePath_UCS(obj.chid, temporaryNode.path + "," + obj.chid.getNodename()), (obj.weight + markedValue));
            }
        }
    }

    // algorithem execution , that work with periorityQueue
    public void Algorithm() {

        VertexWithroutePath_UCS temporaryNode;
        if (!this.startingPoint.equals(this.Endingpoint)) {
            //------------------- add starting point to visitedArraylist
            _vertexHASvisited.add(startingPoint);
            int markedCost = 0;
            //------------------- enqueue all the edges of starting point
            enqueueAllchild(new VertexWithroutePath_UCS(startingPoint, startingPoint.getNodename()), markedCost);

            while (!periorityQueue.isEmpty()) {
                markedCost = this.periorityQueue.minPeriority();
                temporaryNode = periorityQueue.dequeue();
                _vertexHASvisited.add(temporaryNode.vertex);
                if (!temporaryNode.vertex.equals(this.Endingpoint)) {
                    enqueueAllchild(temporaryNode, markedCost);
                } else {
                    setPath(temporaryNode, markedCost);
                    break;
                }
            }
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(VertexWithroutePath_UCS nodeData, int cost) {
        this.path = nodeData.path;
        this.Cost = cost;
    }

    // check that a vertex is already visitedd or not
    public boolean isVisited(vertex obj) {
        for (vertex vert : _vertexHASvisited) {
            if (vert.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    public String getExecution() {
        String allnodes = "";
        for (vertex vert : _vertexHASvisited) {
            allnodes += vert.nodename + ",";
        }
        return allnodes;
    }

    public class VertexWithroutePath_UCS {

        // a vertex and its path
        public vertex vertex;
        public String path;

        public VertexWithroutePath_UCS(vertex vert, String path) {
            this.vertex = vert;
            this.path = path;
        }
    }
}
