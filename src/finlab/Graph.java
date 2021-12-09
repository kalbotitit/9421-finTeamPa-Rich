package finlab;


import java.io.*;
import java.util.*;

public class Graph {

    private Map<Character, LinkedList<VertexEdge>> adjList = new HashMap<>();
    private boolean directed;
    private String graphType;

    Graph(){

    }

    Graph(File f) throws IOException {
        readCSV(f);
    }

    public void readCSV(File f) throws IOException {
        final int I = 0;        // constant index of element of every node
        LinkedList<VertexEdge> list;
        char key;
        adjList.clear();
        BufferedReader bfr = new BufferedReader(new FileReader(f));
        String line = bfr.readLine();
        do{
            if (line == null) break;
            String[] parts = line.split(",");
            graphType = parts[I];
            if(line.contains("UN")) {
                undirectedGraph(bfr);
                break;
            } else if(line.contains("WEIGHTED")){
                graphType = line;
                continue;
            }
            list = new LinkedList<>();
            key = parts[0].charAt(0);
            for (int i = 1; i < parts.length; i += 2){
                list.add(new VertexEdge(parts[i].charAt(I), Integer.parseInt(parts[i+1])));
            }
            adjList.put(key, list);
            line = bfr.readLine();

        }while(true);

    }

    private void undirectedGraph(BufferedReader bfr) throws IOException {
        final int WEIGHT = 1;   // assign 1 to every edge weight of the graph
        final int I = 0;        // constant index of element of every node
        LinkedList<VertexEdge> list;    // list of connected node to the base/starting node
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
    }

    public String getGraphType(){
        return graphType;
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
    }

}
