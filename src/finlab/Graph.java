package finlab;


import java.io.*;
import java.util.*;

public class Graph {

    private Map<Character, LinkedList<VertexEdge>> adjList;
    private boolean directed;
    private String graphType;

    Graph(){

    }

    Graph(BufferedReader bfr) throws IOException {
        readWghtGraphCSV(bfr);
    }

    public void readWghtGraphCSV(BufferedReader bfr) throws IOException {
        final int I = 0;        // constant index of element of every node
        LinkedList<VertexEdge> list;
        char key;
        adjList = new HashMap<>();
        String line = bfr.readLine();
        while (line != null) {
            String[] parts = line.split(",");
            list = new LinkedList<>();
            key = parts[0].charAt(0);
            for (int i = 1; i < parts.length; i += 2) {
                list.add(new VertexEdge(parts[i].charAt(I), Integer.parseInt(parts[i + 1])));
            }
            adjList.put(key, list);
            line = bfr.readLine();
        }
        System.out.println(adjList);

    }

    public void readUnWghtGraphCSV(BufferedReader bfr) throws IOException {
        final int WEIGHT = 1;   // assign 1 to every edge weight of the graph
        final int I = 0;        // constant index of element of every node
        LinkedList<VertexEdge> list;    // list of connected node to the base/starting node
        adjList = new HashMap<>();
        char key;   // base/starting node

        String line = bfr.readLine();
        while (line != null) {
            String[] parts = line.split(",");
            // create a new list for every key
            list = new LinkedList<>();
            key = parts[0].charAt(I);
            for (int i = 1; i < parts.length; i++) {
                char e = parts[i].charAt(I);
                list.add(new VertexEdge(e, WEIGHT));
            }
            adjList.put(key, list);
            line = bfr.readLine();
        }
        System.out.println(adjList);
    }

    public String getGraphType(){
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    private static class VertexEdge {

        char element;
        int weight;

        VertexEdge(){}

        VertexEdge(char e, int w){
            this.element = e;
            this.weight = w;
        }

        public char getElement() {
            return element;
        }

        public void setElement(char element) {
            this.element = element;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public String toString(){
            return "(" + element + "," + weight + ")";
        }
    }

}
