package BSTgraph_;

public class Node {

    public vertex data;
    public Node left;
    public Node right;

    public Node(vertex ver) {
        this.data = ver;
        left = null;
        right = null;
    }

    // return data
    public vertex getData() {
        return data;
    }
//   set data

    public void setData(vertex data) {
        this.data = data;
    }
//    return left child

    public Node getLeft() {
        return left;
    }

    //    set left child of node
    public void setLeft(Node left) {
        this.left = left;
    }

    // return right child 
    public Node getRight() {
        return right;
    }
//    set right child of node

    public void setRight(Node right) {
        this.right = right;
    }
    
    public void clear() {
        this.data = null;
        left = null;
        right = null;
    }
    
}
