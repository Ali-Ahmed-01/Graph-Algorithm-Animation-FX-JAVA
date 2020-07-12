package BSTgraph_;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class Graph {

    private static Graph graph;
    Tree BinTree;
    int NodeNumber = 0;

    private Graph() {
        graph = null;
        BinTree = Tree.getBST();
    }

    // singleton class :: the singleton pattern is a design pattern that restricts the instantiation of a class to one object.
    public static synchronized Graph getGraph() {
        if (graph == null) {
            graph = new Graph();
        }
        return graph;
    }

    // Read the graph from text file 
    public void readGraphFromTextfile(String filename) {
        BufferedReader buffer = null;
        try {
            // file open 
            buffer = new BufferedReader(new FileReader(filename));
            String data = buffer.readLine();
            // input the graph with edges and their cost 
            while (data != null) {
                // remove all the other stuff from line 
                String[] str = (data.replaceAll("[()]", " ").replaceAll(",", " ")).split(" ");
                if (str.length == 3) {
                    insertGraph(str[0], str[1], Integer.parseInt(str[2]));
                } else {
                    inserSingleNodeINTOGraph(data);
                }
                data = buffer.readLine();

            }

            buffer.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void place_heuristic_vales(String nodename, String itsheuristic) {
        // assign  the heuristic value to every node by reading text file
        if (BinTree.isPresent(nodename)) {
            vertex tempV = BinTree.getNode(nodename);
            tempV.heuristic = Integer.parseInt(itsheuristic);
        } else {
            vertex ver2 = new vertex(nodename, NodeNumber);
            ver2.heuristic = Integer.parseInt(itsheuristic);
        }
    }

    private void Read_heuristic_vales() {
        BufferedReader buffer = null;
        try {
            String data;
            // file open 
            buffer = new BufferedReader(new FileReader("_graph_heuristic.txt"));
            data = buffer.readLine();
            // assign  the heuristic value to every node by reading text file
            while (data != null) {
                // remove all the other stuff from line 
                String[] str = (data.replaceAll("[()]", " ").replaceAll(",", " ")).split(" ");
                // assign  the heuristic value to every node by reading text file
                if (BinTree.isPresent(str[0])) {
                    vertex tempV = BinTree.getNode(str[0]);
                    tempV.heuristic = Integer.parseInt(str[1]);
                } else {
                    vertex ver2 = new vertex(str[0], NodeNumber);
                    ver2.heuristic = Integer.parseInt(str[1]);
                }
                data = buffer.readLine();
            }
            buffer.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    // Read the graph from text file 
    public void inserGraph(vertex node) {
        BinTree.insert(node);
    }

    public void insertGraph(String nodename, String edgeOfNode, int weightBWnodes) {
        if (!BinTree.isPresent(nodename)) {
            vertex v = new vertex(nodename, NodeNumber);
            inserGraph(v);
            addEdges(v, edgeOfNode, weightBWnodes);

        } else {
            addEdges(BinTree.getNode(nodename), edgeOfNode, weightBWnodes);
        }
    }

    // make edges in graph
    public void addEdges(vertex v, String nodename, int weightBWnodes) {
        if (BinTree.isPresent(nodename)) {
            vertex tempV = BinTree.getNode(nodename);

            if (!BinTree.edges_isPresent(v, tempV)) {
                Edge edge = new Edge(tempV, weightBWnodes);
                v.setNeighbours(edge);
                Edge edgeTemp = new Edge(v, weightBWnodes);
                tempV.setNeighbours(edgeTemp);
            }
        } else {
            // if Edge is not present then  add that Node
            vertex v2 = new vertex(nodename, NodeNumber);
            inserGraph(v2);
            addEdges(v, nodename, weightBWnodes);
        }
    }

    // print the graph in text file
    public void graphPrint(String filename) {
        BinTree.printAllData(filename);
//        JOptionPane.showMessageDialog(null, "Graph has stored in file");
    }

    public void changeNameOFnode(String oldName, String nameSet) {
        BinTree.getNode(oldName).nodename = nameSet;

    }

    public void inserSingleNodeINTOGraph(String nodename) {
        BinTree.insert(new vertex(nodename, NodeNumber));
    }

    public void setWeight(String parent, String child, String weight) {
        BinTree.setWeight(parent, child, weight);
    }

    public void clearAll() {
        this.BinTree.root = null;
        graph = null;
    }

    public void remove(String nodeC) {
        vertex verC = BinTree.getNode(nodeC);
        for (Edge edge : verC.getNeighbours()) {
            for (int i = 0; i < edge.chid.neighbours.size(); i++) {
                if (edge.chid.neighbours.get(i).chid.equals(verC)) {
                    edge.chid.neighbours.remove(i);
                    break;
                }
            }
        }
        verC.neighbours.clear();
    }
}
