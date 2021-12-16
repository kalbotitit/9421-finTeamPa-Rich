package finlab;


import java.io.*;
import java.util.*;

public class Graph {

    private Map<Character, LinkedList<VertexEdge>> adjList;
    private Map<Character, VertexEdge> pathCost;
    private String graphType;

    Graph(){
        adjList = new HashMap<>();
        pathCost = new HashMap<>();
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
                list.add(new VertexEdge(parts[i].charAt(I), Integer.parseInt(parts[i + 1])));
            }
            adjList.put(key, list);
            line = bfr.readLine();
        }
        System.out.println(adjList);

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
                list.add(new VertexEdge(e, WEIGHT));
            }
            adjList.put(key, list);
            line = bfr.readLine();
        }
        System.out.println(adjList);
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

    public String dfs(Character start) throws StackUnderflowException{
        ArrayList<Character> arrayList = new ArrayList<>(){
            public String toString(){
                StringBuilder string = new StringBuilder();
                for(Character character : this) string.append(" -> ").append(character);
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

    private void generatePathCost(Character start){
        List<Character> seenVertex = new ArrayList<>();
        Queue<VertexEdge> queue = new PriorityQueue<>();
        /*
            initialize the previous vertex to empty and make the cost to large number

            for this instance, in VertexEdge, the char element will be treated as the previous vertex
            and for int weight is the cost of travel
         */
        for(Character d: adjList.keySet())
            pathCost.put(d, new VertexEdge('\0', Integer.MAX_VALUE));

        // make the cost of start vertex to zero
        pathCost.put(start, new VertexEdge(start, 0));
        seenVertex.add(start);
        char key = start;

        while (!queue.isEmpty()){
            queue.addAll(adjList.get(key));
            int i = 0;
            for (Map.Entry<Character, LinkedList<VertexEdge>> entry: adjList.entrySet()){

                if (entry.getValue().get(i++) == queue.peek()){

                    if (!seenVertex.contains(queue.peek().getElement())) {

                        seenVertex.add(queue.peek().getElement());
                        Character prevVertex = entry.getKey();
                        int weightVertex = pathCost.get(prevVertex).getWeight();
                        pathCost.put(key, new VertexEdge(prevVertex, weightVertex + queue.peek().weight));
                        break;

                    } // end second if

                } // end first if

            }// end for loop
            key = queue.poll().getElement();
        }
    } // end pathCode method

    public String shortestPath(Character start, Character end){
        generatePathCost(start);
        StringBuilder path = new StringBuilder();
        int cost;
        char key = end;
        for (int i = 0; i < pathCost.size(); i++){
            if (key == start) break;
            if (key == end) cost = pathCost.get(key).getWeight();
            path.append(" -> ").append(key);
            key = pathCost.get(key).getElement();
        }
        path.reverse();
        return path.toString();
    }

    public String getGraphType(){
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }


    private static class VertexEdge implements Comparable<VertexEdge>{

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

        @Override
        public int compareTo(VertexEdge o) {
            if (weight < o.weight) return -1;
            else if (weight > o.weight) return 1;
            return 0;
        }
    }

}
