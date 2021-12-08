package finlab;


import java.io.*;
import java.util.*;

public class Graph {

    private Map<Character, LinkedList<VertexEdge>> adjList = new HashMap<>();
    private boolean directed;
    private String graphType;

    Graph(){

    }

    Graph(File f){
        readCSV(f);
    }

    public void readCSV(File f)  {
        final int I = 0;
        LinkedList<VertexEdge> list = new LinkedList<>();
        VertexEdge ver = new VertexEdge();
        char key;
        adjList.clear();
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))){
            String line = bfr.readLine();
            do{
                if (line == null) break;
                String[] parts = line.split(",");
                if(line.contains("UN")) {
                    undirectedGraph(bfr);
                    graphType = parts[I];
                    break;
                } else if(line.contains("WEIGHTED")){
                    graphType = line;
                    continue;
                }

                key = parts[0].charAt(0);
                for (int i = 1; i < parts.length; i += 2){
                    ver.setElement(parts[i].charAt(I));
                    ver.setWeight(Integer.parseInt(parts[i+1]));
                    list.add(ver);
                }

                adjList.put(key, list);
                list.clear();
                line = bfr.readLine();

            }while(true);

        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    private void undirectedGraph(BufferedReader bfr){
        // assign one to every edge of the graph
        final int WEIGHT = 1;
        final int I = 0;
        LinkedList<VertexEdge> list = new LinkedList<>();
        VertexEdge ver = new VertexEdge();
        char key;


        try {
            String line = bfr.readLine();
            while (line != null) {

                String[] parts = line.split(";");
                key = parts[0].charAt(I);
                for (int i = 1; i < parts.length; i++) {
                    ver.setElement(parts[i].charAt(I));
                    ver.setWeight(WEIGHT);
                    list.add(ver);
                }
                adjList.put(key, list);
                list.clear();
                line = bfr.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
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
