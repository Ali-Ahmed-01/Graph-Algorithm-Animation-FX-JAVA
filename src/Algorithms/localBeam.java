package Algorithms;

import BSTgraph_.Edge;
import BSTgraph_.Tree;
import BSTgraph_.vertex;
import java.util.ArrayList;
import GUI.mainC;
import javax.swing.JOptionPane;

public class localBeam {

    public ArrayList<vertex> _vertexHASvisited;
    public ArrayList<vertex> _vertexHASvisitedAll;
    public ArrayList<String> allPath;

    public ArrayList<vertex> Start_vertexVisitedAll;

    vertex startingPoint, Endingpoint;
    // path if found

    int limit;

    public localBeam(int limit) {
        this.limit = limit;
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
        for (vertex vert : _vertexHASvisitedAll) {
            if (vert.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisited_for_K(vertex obj) {
        for (vertex vert : Start_vertexVisitedAll) {
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

    int presentCost = 0;

    public boolean flag, cantSolve;
    int h_N;

    public void AlgoDFS(vertex vr) {
        if (vr.nodename.equals(Endingpoint.nodename) || vr.equals(Endingpoint)) {
            flag = false;
        }
        _vertexHASvisited.add(vr);
        _vertexHASvisitedAll.add(vr);

        if (flag) {
            boolean lowest = false;
            vertex tempVr = null;
            for (Edge adjEdge : vr.getNeighbours()) {
                if (!isVisited(adjEdge.chid)) {
                    if (adjEdge.chid.heuristic < h_N) {
                        lowest = true;
                        h_N = adjEdge.chid.heuristic;
                        tempVr = adjEdge.chid;
                    }

                }
            }
            if (lowest) {
                AlgoDFS(tempVr);
            } else {
                cantSolve = false;
            }
        }
    }

    public void Algorithm() {
        System.out.println("AlgoStart");
        allPath = new ArrayList<>();
        _vertexHASvisited = new ArrayList<>();
        _vertexHASvisitedAll = new ArrayList<>();
        Start_vertexVisitedAll = new ArrayList<>();

        flag = true;
        cantSolve = true;
        if (startingPoint != null) {
            if (startingPoint.neighbours.size() >= limit) {

                for (int i = 0; i < limit; i++) {

                    int smallest = Integer.MAX_VALUE;
                    vertex tempVr = null;
                    for (Edge adjEdge : startingPoint.getNeighbours()) {
                        if (!isVisited_for_K(adjEdge.chid)) {
                            if (adjEdge.chid.heuristic < smallest) {
                                tempVr = adjEdge.chid;
                            }

                        }
                    }
                    Start_vertexVisitedAll.add(tempVr);
                }
                for (int i = 0; i < Start_vertexVisitedAll.size() && flag && cantSolve; i++) {
                    _vertexHASvisited = new ArrayList<>();
                    _vertexHASvisited.add(startingPoint);
                    h_N = Start_vertexVisitedAll.get(i).heuristic;
                    AlgoDFS(Start_vertexVisitedAll.get(i));
                    allPath.add(getExecution());
                }
            }
        }
    }
}
