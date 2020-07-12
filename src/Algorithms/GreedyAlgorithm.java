package Algorithms;

import BSTgraph_.Edge;
import BSTgraph_.Tree;
import BSTgraph_.vertex;
import java.util.ArrayList;
import GUI.mainC;
import javax.swing.JOptionPane;

public final class GreedyAlgorithm {

    // an ArrayList that help to contain visited vertex , if a vertex has visited then that vertex add to this arrayList
    public ArrayList<vertex> _vertexHASvisited;
    private Greedy_periorityQueue periorityQueue;    // a periorityQueue , help in algo Execution
    vertex startingPoint, Endingpoint;    // starting point and ending point 
    public String path;
    public int Cost;

    public GreedyAlgorithm() {
        init();
    }

    public void init() {
        // arrayList init
        _vertexHASvisited = new ArrayList<>();
        this.periorityQueue = new Greedy_periorityQueue();
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

    public void enqueueAllchild(Greedy_Vpath temporaryNode) {
        for (Edge obj : temporaryNode.vertex.getNeighbours()) {
            if (!isVisited(obj.chid)) {
                this.periorityQueue.enqueue(new Greedy_Vpath(obj.chid, temporaryNode.path + "," + obj.chid.nodename, obj.weight + temporaryNode.dist_Cost, obj.chid.heuristic + temporaryNode.path_Cost), (obj.chid.heuristic));
            }
        }
    }

    public void Algorithm() {

        Greedy_Vpath temporaryNode;
        if (!this.startingPoint.equals(this.Endingpoint)) {
            //------------------- enqueue all the edges of starting point
            _vertexHASvisited.add(startingPoint);
            enqueueAllchild(new Greedy_Vpath(startingPoint, startingPoint.nodename, 0, 0));
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

    // check that a vertex is already visitedd or not
    public boolean isVisited(vertex obj) {
        for (vertex vert : _vertexHASvisited) {
            if (vert.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    // return all the detail of appliedd algo
    public String Detail() {
        String detail = "Total number of nodes     :" + Tree.getBST().getTotalnodes() + "\n";
        detail = detail + "Expanded/evaluated  nodes :" + (this._vertexHASvisited.size() - 1);
        return detail;
    }

    public void setPath(Greedy_Vpath Nodedata) {
        path = Nodedata.path;
        Cost = Nodedata.dist_Cost;

    }

    public String getExecution() {
        String allnodes = "";
        for (vertex vert : _vertexHASvisited) {
            allnodes += vert.nodename + ",";
        }
        return allnodes;
    }

    // a class that help a Greedy algo to save path for a  vertex and its  path cost as well as huristic cost
    public class Greedy_Vpath {

        // a vertex that has path and its cost 
        public vertex vertex;
        public String path;
        //distance cost
        public int dist_Cost;
        // huristic cost
        public int path_Cost;

        public Greedy_Vpath(vertex vert, String path, int cost, int pathCost) {
            this.vertex = vert;
            this.path = path + " ";
            this.dist_Cost = cost;
            this.path_Cost = pathCost;
        }
    }
}
