package DS;

import java.util.ArrayList;
import BSTgraph_.*;

public class Vqueue {

    ArrayList<vertex> Vertex;

    public Vqueue() {
        Vertex = new ArrayList<>();
    }

    public boolean isEmpty() {
        if (Vertex.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void enqueue(vertex ver) {
        this.Vertex.add(ver);
    }

    public vertex dequeue() {
        vertex v = this.Vertex.get(0);
        this.Vertex.remove(v);
        return v;
    }

}
