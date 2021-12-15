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

    public static void DijkstraShortestPath( int[][] graph, int source ) {
        int count = graph.length;
        boolean[] visitedVertex = new boolean[count];
        int[] distance = new int[count];
        for (int i = 0; i < count; i++) {
            visitedVertex[i] = false;
            distance[i] = Integer.MAX_VALUE;
        }

        // Distance of self loop is zero
        distance[source] = 0;
        for (int i = 0; i < count; i++) {

            // Update the distance between neighbouring vertex and source vertex
            int u = findMinDistance(distance, visitedVertex);
            visitedVertex[u] = true;

            // Update all the neighbouring vertex distances
            for (int v = 0; v < count; v++) {
                if (!visitedVertex[v] && graph[u][v] != 0 && (distance[u] + graph[u][v] < distance[v])) {
                    distance[v] = distance[u] + graph[u][v];
                }
            }
        }
        for (int i = 0; i < distance.length; i++) {
            System.out.printf("Distance from %s to %s is %s%n", source, i, distance[i]);
        }
    }

    // Finding the minimum distance
    private static int findMinDistance( int[] distance, boolean[] visitedVertex ) {
        int minDistance = Integer.MAX_VALUE;
        int minDistanceVertex = -1;
        for (int i = 0; i < distance.length; i++) {
            if (!visitedVertex[i] && distance[i] < minDistance) {
                minDistance = distance[i];
                minDistanceVertex = i;
            }
        }
        return minDistanceVertex;
    }

    public String getGraphType(){
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    private void visitedVertex(){

    }

    private class VertexEdge {

        char element;
        int weight;

        VertexEdge(){}

        VertexEdge(char e, int w){
            this.element = e;
            this.weight = w;
        }
        public String breadthFirstSearch(Character start) throws QueueNullException{
            ArrayList<Character> arrayList = new ArrayList<>(){
                public String toString(){
                    StringBuilder string = new StringBuilder();
                    for(Character character : this) string.append(" -> ").append(character);
                    return string.toString();
                }
            };
            MyQueue<Character> queue = new MyQueue<Character>();
            queue.enqueue(start);
            char key;
            while(!queue.isEmpty()){
                Character curr = queue.dequeue();
                key = curr;
                if(!arrayList.contains(curr))
                    arrayList.add(curr);
                for(int i = 0; i < adjList.get(key).size(); i++){
                    if(!arrayList.contains(adjList.get(key).get(i).getElement()))
                        queue.enqueue(adjList.get(key).get(i).getElement());
                }
            }
            return arrayList.toString();
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
