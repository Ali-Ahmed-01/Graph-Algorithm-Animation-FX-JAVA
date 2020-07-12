package Algorithms;

import Algorithms.GreedyAlgorithm.Greedy_Vpath;

public class Greedy_periorityQueue {
// a class of periorityQueue

    Greedy_Vpath Vertex[];
    int periority[];
    int index = -1;
    int size = 100;//number of nodes

    public Greedy_periorityQueue() {
        size = 100;//number of nodes
        Vertex = new Greedy_Vpath[size];
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

    public void enqueue(Greedy_Vpath ver, int proir) {
        index++;
        this.Vertex[index] = ver;
        this.periority[index] = proir;
    }
    public int size(){
        return index+1;
    }

    public Greedy_Vpath dequeue() {
        int minPeri = periority[0];
        int ind = 0;
        for (int i = 1; i <=index; i++) {
            if (periority[i] < minPeri) {
                minPeri = periority[i];
                ind = i;
            }
        }
        Greedy_Vpath v = this.Vertex[ind];
        correction(ind);
        return v;
    }

    public void correction(int ind) {
        for (int i = ind ; i <=index; i++) {
            this.Vertex[i] = this.Vertex[i+1];
            this.periority[i] = this.periority[i+1];
        }
        this.index--;
    }
}