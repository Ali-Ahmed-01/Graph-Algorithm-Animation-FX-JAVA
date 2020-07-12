package Algorithms;

import BSTgraph_.Edge;
import BSTgraph_.Tree;
import BSTgraph_.vertex;
import java.util.ArrayList;
import GUI.mainC;
import javax.swing.JOptionPane;

public class DFS {

    public ArrayList<vertex> _vertexHASvisited;

    vertex startingPoint, Endingpoint;

    public DFS() {
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

        this.Endingpoint = Tree.getBST().getNode(mainC.goal);
        // algorithm execution

        if (Endingpoint != null) {
            Algorithm();
        } else {
            JOptionPane.showMessageDialog(null, "", "Goal is not define", JOptionPane.ERROR_MESSAGE);
        }
    }

//    private void Algorithm_THnodes() {
//        String nodename__ = Endingpoint.nodename;
//        
//        Node node = Tree.getBST().root;
//        Stack stk = new Stack(1000);
//
//        while (stk.isEmpty() == false || node != null) {
//            if (node != null) {
//                stk.push(node);
//                _nodeHASvisited.add(node);
//                node = node.left;
//            } else {
//                node = stk.pop();
//                
//                if (node.data.nodename.equals(nodename__.trim())) {
//                    System.out.println("found");
//                    break;
//                }
//
//                node = node.right;
//            }
//        }
//    }
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

        if (startingPoint != null) {
            flag = true;
            AlgoDFS(startingPoint);
        }
    }

    boolean flag;

    public void AlgoDFS(vertex vr) {
        if (vr.nodename.equals(Endingpoint.nodename) || vr.equals(Endingpoint)) {
            flag = false;
        }
        _vertexHASvisited.add(vr);
        if (flag) {
            for (Edge adjEdge : vr.getNeighbours()) {
                if (!isVisited(adjEdge.chid)) {
                    if (flag) {
                        AlgoDFS(adjEdge.chid);
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
