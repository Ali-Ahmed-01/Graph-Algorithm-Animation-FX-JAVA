/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import BSTgraph_.vertex;
import java.util.ArrayList;

/**
 *
 * @author ali
 */
public class VStack {

    public ArrayList<vertex> Vertex;

    public VStack() {
        Vertex = new ArrayList<>();
    }

    public void push(vertex ver) {
        this.Vertex.add(ver);

    }

    public vertex pop() {
        System.out.println("inpop : "+Vertex.size());
        vertex v = this.Vertex.get(Vertex.size() - 1);
        this.Vertex.remove(v);
        return v;
    }

    public vertex topStk() {
        return this.Vertex.get(Vertex.size() - 1);
    }

    public boolean isEmpty() {
        return Vertex.isEmpty();

    }

}
