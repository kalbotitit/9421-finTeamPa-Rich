package finlab;


import java.io.*;
import java.util.*;

/**
 * {@code Graph} class read csv file that contains the representation for a graph.
 * Adjacency list is used for representing the graph. Graph can be undirected or directed and unweighted or weighted.
 *
 * <p>Character is used for the vertices.
 * Hashmap for storing the representation of graph from the csv file.</p>
 */
public class Graph {

    private Map<Character, LinkedList<VertexEdge>> adjList;
    private String graphType;

    Graph(){
        adjList = new HashMap<>();
    }

    Graph(BufferedReader bfr) throws IOException {
        readWghtGraphCSV(bfr);
    }

    /**
     * Read a csv file that contains the representation of both weighted directed and undirected graph
     * @param bfr holds the csv file
     * @throws IOException if there is an error in I0
     */
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
                list.add(new VertexEdge(key, parts[i].charAt(I), Integer.parseInt(parts[i + 1])));
            }
            adjList.put(key, list);
            line = bfr.readLine();
        }

    }

    /**
     * Read a csv file that contains the representation of both unweighted directed and undirected graph
     * @param bfr holds the csv file
     * @throws IOException if there is an error in I0
     */
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
                list.add(new VertexEdge(key, e, WEIGHT));
            }
            adjList.put(key, list);
            line = bfr.readLine();
        }
    }

    /**
     * Return the path of breadth first search result from the source vertex
     * @param start source vertex
     * @return path of breadth first search result from the source vertex
     * @throws QueueNullException when the queue is null
     */
    public String breadthFirstSearch(Character start) throws QueueNullException{
        isVertex(start);
        ArrayList<Character> arrayList = new ArrayList<>(){
            public String toString(){
                StringBuilder string = new StringBuilder();
                for(Character character : this) string.append(", ").append(character);
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
            int n = adjList.get(key).size();
            for(int i = 0; i < n; i++){
                if(!arrayList.contains(adjList.get(key).get(i).getElement()))
                    queue.enqueue(adjList.get(key).get(i).getElement());
            }
        }
        return arrayList.toString();
    }

    /**
     * Return the path of depth first search result from the source vertex
     * @param start source vertex
     * @return path of depth first search result from the source vertex
     * @throws StackUnderflowException when the queue is null
     */
    public String depthFS(Character start) throws StackUnderflowException{
        isVertex(start);
        ArrayList<Character> arrayList = new ArrayList<>(){
            public String toString(){
                StringBuilder string = new StringBuilder();
                for(Character character : this) string.append(character).append(", ");
                return string.toString();
            }
        };
        MyStack<Character> stack = new MyStack<Character>();
        stack.push(start);
        char key;
        while(!stack.isEmpty()){
            Character curr = stack.pop();
            key = curr;
            if(!arrayList.contains(curr))
                arrayList.add(curr);
            for(int i = 0; i < adjList.get(key).size(); i++){
                if(!arrayList.contains(adjList.get(key).get(i).getElement()))
                    stack.push(adjList.get(key).get(i).getElement());
            }
        }
        return arrayList.toString();
    }

    /**
     * Return the shortest path from the source vertex to end vertex
     * @param start source vertex
     * @param end target vertex
     * @return shortest path from the source vertex to end vertex
     */
    /*
        Algorithm:
            shortestPath(start, end)
                Generate path cost table
                vertex <- end
                For all vertices in path cost table
                    If key is equal to start
                        result <- add vertex
                        End the loop
                    If key is equal to end
                        cost <- vertex cost in path cost table
                    result <- add vertex
                    Update vertex of the previous vertex of current vertex
                Reverse the result
    */
    public List<String> shortestPath(Character start, Character end){
        isVertex(start);
        isVertex(end);
        List<String> result = new ArrayList<>(); // both the path and cost
        Map<Character, VertexEdge> pathCost; // map for path cost table
        pathCost = generatePathCostTable(start);
        StringBuilder path = new StringBuilder();
        int cost =0;
        char key = end;
        for (int i = 0; i < pathCost.size(); i++){
            if (key == start){
                path.append(key).append(" >-");
                break;
            }
            if (key == end) cost = pathCost.get(key).weight; // get the total cost of traversal
            path.append(key).append(" >- ");
            key = pathCost.get(key).prev;
        }
        path.reverse();
        result.add(path.toString());
        result.add(String.valueOf(cost));
        return result;
    }

    /**
     * Return map of path cost table from source vertex to all vertices
     * @param start source vertex
     * @return map of path cost table from source vertex to all vertices
     */
    private Map<Character, VertexEdge> generatePathCostTable(Character start){
        Map<Character, VertexEdge> pathCost = new HashMap<>();
        List<Character> seenVertex = new ArrayList<>();
        Queue<VertexEdge> queue = new PriorityQueue<>();
        Object[] vertices = adjList.keySet().toArray();
        for (int i = 0; i < vertices.length; i++)
            pathCost.put((Character) vertices[i], new VertexEdge('\0', Integer.MAX_VALUE));

        // make the cost of start vertex to zero
        pathCost.put(start, new VertexEdge('\0', 0));
        seenVertex.add(start);
        VertexEdge key = new VertexEdge();
        char prev;
        queue.addAll(adjList.get(start));

        while (!queue.isEmpty()){
            key = queue.poll();
            prev = key.prev;
            int currCost = pathCost.get(key.element).weight;
            int cost = pathCost.get(prev).getWeight() + key.weight;
            if (cost < currCost) {
                pathCost.put(key.element, new VertexEdge(prev, cost));
                queue.addAll(adjList.get(key.element));
            }

        }
        return pathCost;
    } // end pathCost method

    /**
     * Check the input if it is in the list of vertices
     * @param s input
     * @throws InputMismatchException when input is not the list of vertices
     */
    private void isVertex(Character s) throws InputMismatchException{
        Object[] vertices = adjList.keySet().toArray();
        for (Object v : vertices){
            if (v.equals(s))
                return;
        }
        throw new InputMismatchException();
    }

    /**
     * Return the content of the csv file for unweighted graph
     * @return the content of the csv file
     */
    public String contentUnWght(){
        StringBuilder sb = new StringBuilder();
        Object[] vertex = adjList.keySet().toArray();
        for (Object v : vertex){
            sb.append(v).append(" -> ");
            for (int i =0; i < adjList.get(v).size(); i++){
                sb.append(adjList.get(v).get(i).element).append(", ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Return the content of the csv file for weighted graph
     * @return the content of the csv file
     */
    public String contentWght(){
        StringBuilder sb = new StringBuilder();
        Object[] vertex = adjList.keySet().toArray();
        for (Object v : vertex){
            sb.append(v).append(" -> ");
            for (int i =0; i < adjList.get(v).size(); i++){
                sb.append(adjList.get(v).get(i).element).append(adjList.get(v).get(i).weight).append(", ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Return the type of graph loaded
     * @return the type of graph loaded
     */
    public String getGraphType(){
        return graphType;
    }

    /**
     * Set the type of the graph loaded
     * @param graphType ype of the graph loaded
     */
    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }



    private static class VertexEdge implements Comparable<VertexEdge>{

        private char prev;
        private char element;
        private int weight;

        VertexEdge(){
            prev = 'A';
            element = 'B';
            weight = 1;
        }

        VertexEdge(char p, int w){
            this.prev = p;
            this.weight = w;
        }

        VertexEdge(char p, char e, int w){
            this.prev = p;
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

        public char getPrev() {
            return prev;
        }

        public void setPrev(char prev) {
            this.prev = prev;
        }
        @Override
        public String toString(){
            return (prev + " " + element + " " + weight);
        }

        @Override
        public int compareTo(VertexEdge pc){
            if (weight < pc.weight) return -1;
            else if (weight > pc.weight) return 1;
            return 0;
        }

    }



}
