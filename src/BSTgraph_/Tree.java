package BSTgraph_;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import GUI.NodeCircle;

public class Tree {

    public Node root;
    public int totalnodes = 0;
    private static Tree BST;

    private Tree() {
        root = null;
    }

    public static synchronized Tree getBST() {
        if (BST == null) {
            BST = new Tree();
        }
        return BST;
    }
// BST build

    public void insert(vertex inData) {
        Node newNode = new Node(inData);
        if (root == null) {
            root = newNode;
        } else {
            Node current = root;
            while (true) {
                if (current.data.nodenum >= inData.nodenum) {
                    if (current.left == null) {
                        current.left = newNode;
                        totalnodes++;
                        break;
                    } else {
                        current = current.left;
                    }
                } else if (current.right == null) {
                    current.right = newNode;
                    totalnodes++;
                    break;
                } else {
                    current = current.right;
                }
            }
        }
    }

    // check that the vertex with city name is present in tree or not 
    public boolean isPresent(String nodename) {
        Node node = root;
        Stack stk = new Stack(100);
        while (stk.isEmpty() == false || node != null) {
            if (node != null) {
                stk.push(node);
                node = node.left;
            } else {
                node = stk.pop();
                if (node.data.nodename.equals(nodename)) {
                    return true;
                }
                node = node.right;
            }
        }
        return false;
    }

    // check that the edge is present in certin vertex or not
    public boolean edges_isPresent(vertex ver, vertex ver2) {
        for (Edge obj : ver2.getNeighbours()) {
            if (obj.getChid().equals(ver)) {
                return true;
            }
        }
        return false;
    }

    //----------------------- getSpecificVertex => itrative
    public vertex getNode(String nodename__) {
        Node node = root;
        Stack stk = new Stack(1000);

        while (stk.isEmpty() == false || node != null) {
            if (node != null) {
                stk.push(node);
                node = node.left;
            } else {
                node = stk.pop();
                if (node.data.nodename.equals(nodename__.trim())) {
                    return node.data;
                }

                node = node.right;
            }
        }
        return null;
    }

    String fileName;

    public void printAllData(String filename) {
        this.fileName = filename;
        Node node = root;
        Stack stk = new Stack(100);
        if (root != null) {
            while (stk.isEmpty() == false || node != null) {
                if (node != null) {
                    stk.push(node);
                    node = node.left;
                } else {
                    node = stk.pop();
                    if (!node.data.neighbours.isEmpty()) {
                        for (Edge obj : node.data.getNeighbours()) {
                            fileWriter(node.data.nodename + "(" + obj.getChid().nodename + "," + obj.getWeight() + ")");
                        }
                    } else {
                        fileWriter(node.data.nodename);
                    }
                    node = node.right;
                }
            }
        }
    }

    public void fileWriter(String data) {
        // The name of the file to open.
        try {
            // Assume default encoding.
            FileWriter FW
                    = new FileWriter(fileName, true);
            // Note that write() does not automatically
            // append a newline character.
            try ( // Always wrap FileWriter in BufferedWriter.
                    BufferedWriter bufferedWriter = new BufferedWriter(FW)) {
                // Note that write() does not automatically
                // append a newline character.
                bufferedWriter.write(data);
                bufferedWriter.newLine();
                // Always close files.
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public int getTotalnodes() {
        return totalnodes;
    }

    void setWeight(String parent, String child, String weight) {
        vertex p = getNode(parent);
        vertex c = getNode(child);

        for (Edge obj : p.getNeighbours()) {
            if (obj.getChid().equals(c)) {
                obj.weight = Integer.parseInt(weight);
            }
        }
        for (Edge obj : c.getNeighbours()) {
            if (obj.getChid().equals(p)) {
                obj.weight = Integer.parseInt(weight);
            }
        }
    }

    public void remove_(String nodename__) {
        Node node = root;
        Stack stk = new Stack(1000);

        while (stk.isEmpty() == false || node != null) {
            if (node != null) {
                stk.push(node);
                node = node.left;
            } else {
                node = stk.pop();
                if (node.data.nodename.equals(nodename__.trim())) {
                    node.clear();
                    break;
                }

                node = node.right;
            }
        }
    }
}
