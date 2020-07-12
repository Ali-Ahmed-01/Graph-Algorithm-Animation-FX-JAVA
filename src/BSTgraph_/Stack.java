package BSTgraph_;

// a stack 
public class Stack {

    private Node[] array;
    private int size;
    private int Location = -1;

   
    public Stack(int stackSize) {
        this.size = stackSize;
        array = new Node[stackSize];
    }

    public void push(Node toAdd) {
        array[++Location] = toAdd;

    }

    public Node pop() {
        if (!isEmpty()) {
            return array[Location--];
        } else {
            throw new RuntimeException("overflow");
        }
    }

    public Node topStk() {
        return array[Location];
    }

    public boolean isEmpty() {
        if (Location == -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFull() {
        if (Location == size) {
            return true;
        } else {
            return false;
        }
    }
}
