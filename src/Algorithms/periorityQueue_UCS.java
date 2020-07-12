package Algorithms;

import Algorithms.UCS_Algorithm.VertexWithroutePath_UCS;

public class periorityQueue_UCS {

    VertexWithroutePath_UCS Vertex[];
    int periority[];
    int index = -1;
    int size = 100;//number of nodes

    public periorityQueue_UCS() {
        size = 100;//number of nodes
        Vertex = new VertexWithroutePath_UCS[size];
        periority = new int[size];
        index = -1;

    }

    public boolean isEmpty() {
        if (index == -1) {
            return true;
        } else {
            return false;
        }
    }

    public void enqueue(VertexWithroutePath_UCS ver, int proir) {
        index++;
        this.Vertex[index] = ver;
        this.periority[index] = proir;
    }

    public int size() {
        return index + 1;
    }

    public VertexWithroutePath_UCS dequeue() {
        int minPeri = periority[0];
        int ind = 0;
        for (int i = 1; i <= index; i++) {
            if (periority[i] < minPeri) {
                minPeri = periority[i];
                ind = i;
            }
        }
        VertexWithroutePath_UCS v = this.Vertex[ind];
        correction(ind);
        return v;
    }

    public void correction(int ind) {
        for (int i = ind; i <= index; i++) {
            this.Vertex[i] = this.Vertex[i + 1];
            this.periority[i] = this.periority[i + 1];
        }
        this.index--;
    }

    public int minPeriority() {
        int minPeri = periority[0];
        int ind = 0;
        for (int i = 1; i <= index; i++) {
            if (periority[i] < minPeri) {
                minPeri = periority[i];
                ind = i;
            }
        }
        return periority[ind];
    }
}
