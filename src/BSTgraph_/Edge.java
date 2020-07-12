package BSTgraph_;

public class Edge {

    public vertex chid;
    public int weight;

    // edges of vertex( any node )
    public Edge(vertex chid, int weight) {
        this.chid = chid;
        this.weight = weight;
    }

    // return child
    public vertex getChid() {
        return chid;
    }
    
//    return weight between two nodes(Vertex)
    public int getWeight() {
        return weight;
    }
}
