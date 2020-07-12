package BSTgraph_;

import java.util.*;

public class vertex {

    public boolean flag;
    public String nodename;
    public int nodenum;
    public int heuristic;
    public ArrayList<Edge> neighbours;

    public vertex(String CityName, int postelCode) {
        this.nodename = CityName;
        this.nodenum = postelCode;
        this.neighbours = new ArrayList<>();
        flag = false;
    }

    public ArrayList<Edge> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Edge neighbours) {
        this.neighbours.add(neighbours);
    }

    public String getNodename() {
        return nodename;
    }

    public int getNodenum() {
        return nodenum;
    }

    public int getHeuristic() {
        return heuristic;
    }

}
