package Algorithms;

import BSTgraph_.Edge;
import BSTgraph_.Tree;
import BSTgraph_.vertex;
import java.util.ArrayList;
import GUI.mainC;
import javax.swing.JOptionPane;

public final class Astar_algorithm {

    // an ArrayList that help to contain visited vertex , if a vertex has visited then that vertex add to this arrayList
    public ArrayList<vertex> _vertexHASvisited;
    // a periorityQueue , help in algo Execution
    private Astar_periorityQueue periorityQueue;
    // starting point and ending point 
    vertex startingPoint, Endingpoint;
    // path if found
    public String path;
    public int Cost;

    public Astar_algorithm() {
        init();
    }

    public void init() {
        // arrayList init
        _vertexHASvisited = new ArrayList<>();
        this.periorityQueue = new Astar_periorityQueue();
        Algorithm_init();
    }

    public void Algorithm_init() {
        // "A" is starting point as graph 
        this.startingPoint = Tree.getBST().getNode(Tree.getBST().root.data.nodename);
        // "A" is Ending point as graph 
        this.Endingpoint = Tree.getBST().getNode(mainC.goal);
        // algorithm execution

        if (Endingpoint != null) {
            Algorithm();
        } else {
            JOptionPane.showMessageDialog(null, "", "Goal is not define", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void enqueueAllchild(Astar_Vpath temporaryNode) {
        for (Edge adjEdge : temporaryNode.vertex.getNeighbours()) {
            if (!isVisited(adjEdge.chid)) {
                this.periorityQueue.enqueue(new Astar_Vpath(adjEdge.chid, temporaryNode.path + "," + adjEdge.chid.nodename, adjEdge.weight + temporaryNode.distanceCost), adjEdge.weight + adjEdge.chid.heuristic + temporaryNode.distanceCost);
            }
        }
    }

    public void Algorithm() {
        Astar_Vpath temporaryNode;
        if (!this.startingPoint.equals(this.Endingpoint)) {
            _vertexHASvisited.add(startingPoint);
            //------------------- enqueue all the edges nodes
            enqueueAllchild(new Astar_Vpath(startingPoint, startingPoint.nodename, 0));
            //------------------- add starting point to visitedArraylist
            while (!periorityQueue.isEmpty()) {
                temporaryNode = periorityQueue.dequeue();
                _vertexHASvisited.add(temporaryNode.vertex);
                if (!temporaryNode.vertex.equals(this.Endingpoint)) {
                    enqueueAllchild(temporaryNode);
                } else {
                    setPath(temporaryNode);
                    break;
                }
            }
        }
    }

    public String getPath() {
        return path;
    }

    public String Details() {
        String detail = "Total Nodes     :" + Tree.getBST().getTotalnodes() + "\n";
        detail = detail + "Expanded/evaluated nodes :" + (this._vertexHASvisited.size() - 1) + "\n\n";
        return detail;
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

    public void setPath(Astar_Vpath nodeData) {
        path = nodeData.path;
        Cost = nodeData.distanceCost;
    }

    public String getExecution() {
        String allnodes = "";
        for (vertex vert : _vertexHASvisited) {
            allnodes += vert.nodename + ",";
        }
        return allnodes;
    }

    public class Astar_Vpath {

        // a vertex that has path and its cost
        public vertex vertex;
        public String path;
        public int distanceCost;

        public Astar_Vpath(vertex vert, String path, int cost) {
            this.vertex = vert;
            this.path = path + "";
            this.distanceCost = cost;
        }
    }
}
