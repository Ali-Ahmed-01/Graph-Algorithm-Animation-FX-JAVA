package Algorithms;

import BSTgraph_.Edge;
import BSTgraph_.Tree;
import BSTgraph_.vertex;
import java.util.ArrayList;
import GUI.mainC;
import javax.swing.JOptionPane;

public class IDS {

    public ArrayList<vertex> _vertexHASvisited;
    public ArrayList<String> allPath;

    vertex startingPoint, Endingpoint;
    // path if found
    int totalLayers;

    public IDS(int layers) {
        totalLayers = layers;
        init();
    }

    public void init() {
        // arrayList init
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

    public void Algorithm() {
        allPath = new ArrayList<>();
        _vertexHASvisited = new ArrayList<>();
        if (startingPoint != null) {
            flag = true;
            limit = 0;
            while (flag && limit < totalLayers) {
                AlgoDFS(startingPoint, 0);
                limit++;
                allPath.add(getExecution());
                _vertexHASvisited = new ArrayList<>();
            }
        }
    }

    public boolean flag;
    int limit;

    public void AlgoDFS(vertex vr, int reachLimit) {
        if (vr.nodename.equals(Endingpoint.nodename) || vr.equals(Endingpoint)) {
            flag = false;
        }
        _vertexHASvisited.add(vr);
        if (reachLimit == limit) {
            return;
        }
        if (flag) {
            for (Edge adjEdge : vr.getNeighbours()) {
                if (!isVisited(adjEdge.chid)) {
                    if (flag) {
                        AlgoDFS(adjEdge.chid, (reachLimit + 1));
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
