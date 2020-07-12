package Algorithms;

import BSTgraph_.Edge;
import BSTgraph_.Tree;
import BSTgraph_.vertex;
import java.util.ArrayList;
import GUI.mainC;
import javax.swing.JOptionPane;

public class hillClimbing {

    public ArrayList<vertex> _vertexHASvisited;
    public ArrayList<vertex> _vertexHASvisitedAll;
    public ArrayList<String> allPath;

    vertex startingPoint, Endingpoint;
    // path if found

    public hillClimbing() {
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

    public String getExecution() {
        String allnodes = "";
        for (vertex vert : _vertexHASvisited) {
            allnodes += vert.nodename + ",";
        }
        return allnodes;
    }

    int presentCost = 0;

    public void Algorithm() {
        System.out.println("AlgoStart");
        allPath = new ArrayList<>();
        _vertexHASvisited = new ArrayList<>();
        _vertexHASvisitedAll = new ArrayList<>();
        flag = true;
        cantSolve = true;
        if (startingPoint != null) {
            while (flag && cantSolve) {
                h_N = startingPoint.heuristic;
                AlgoDFS(startingPoint);
                
                allPath.add(getExecution());
                _vertexHASvisited = new ArrayList<>();
            }
            for(String s : allPath){
                System.out.println(s);
            }
        }
    }

    public boolean flag,cantSolve;
    int h_N;
    public void AlgoDFS(vertex vr) {
        if (vr.nodename.equals(Endingpoint.nodename) || vr.equals(Endingpoint)) {
            flag = false;
        }
        _vertexHASvisited.add(vr);
        _vertexHASvisitedAll.add(vr);
        
        if (flag) {
            boolean lowest=false;
            vertex tempVr=null;
            for (Edge adjEdge : vr.getNeighbours()) {
                if (!isVisited(adjEdge.chid)) {
                    if(adjEdge.chid.heuristic<h_N){
                        lowest = true;
                        h_N = adjEdge.chid.heuristic;
                        tempVr = adjEdge.chid;
                    }
                      
                }
            }
            if(lowest){
                AlgoDFS(tempVr);
            }else{
                cantSolve=false;
            }
        }
    }
}
