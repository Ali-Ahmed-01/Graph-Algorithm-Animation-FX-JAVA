package Algorithms;

import BSTgraph_.Edge;
import BSTgraph_.Tree;
import BSTgraph_.vertex;
import DS.Vqueue;
import java.util.ArrayList;
import GUI.mainC;
import javax.swing.JOptionPane;

public class BFS {

    public ArrayList<vertex> _vertexHASvisited;

    vertex startingPoint, Endingpoint;
    // path if found

    public BFS() {
        init();
    }

    public void init() {
        // arrayList init
        _vertexHASvisited = new ArrayList<>();
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

    public void Algorithm() {

        vertex vr = startingPoint;
        vertex temp;
        if (vr != null) {
            Vqueue Q = new Vqueue();
            Q.enqueue(vr);
            while (Q.isEmpty() == false) {
                temp = Q.dequeue();
                _vertexHASvisited.add(temp);
                if (!temp.nodename.equals(this.Endingpoint.nodename)) {
                    for (Edge adjEdge : temp.getNeighbours()) {
                        if (!isVisited(adjEdge.chid)) {
                            Q.enqueue(adjEdge.chid);
                        }
                    }
                } else {
                    break;
                }
            }
        }
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
}
