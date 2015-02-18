import java.io.IOException;
import java.util.*;


public class Main {


    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        //check file arugment and load graph.
        if ("graph.txt".equals(args[1])) {
            reader.read(args[1]);
        }

        //define graph
        Graph graph = reader.graph();
        //check flags
        if ("-p1".equals(args[0])) {
            sortPrintNodes(graph);
        }
        else if ("-p2".equals(args[0])) {
            searchForNodesWithXNeighbours(graph, Integer.parseInt(args[2]));
        }
        else if ("-p3".equals(args[0])){
            findFullyConnectedNeighbours(graph);
        }

        else throw new IOException();
    }

    //Prints graph sorted by names of nodes. Containing sorted list of neighbours.
    private static void sortPrintNodes(Graph graph) throws IOException {

        //Sorts graph.nodes = returning sorted List of nodes.
        List<Node> SortedNodeList = sortNodes(graph.nodes());

        //now print nodes
        for (Node n : SortedNodeList) {
            System.out.print(n.name());
            List<Node> neighboursList = sortNodes(n.neighbours());
            for (Node i : neighboursList) {
                System.out.print(" " + i.name());
            }
            //todo check if this works with test, prints new line at the end
            System.out.println();
        }
    }

    //Sorts Set of Nodes, returning List of nodes.
    private static List<Node> sortNodes(Set nodes) throws IOException {
        List<Node> nodeList = new ArrayList(nodes);
        nodeList.sort(new NodeNameComparator());
        return nodeList;

    }
//compares nodenames
    public static class NodeNameComparator implements Comparator<Node> {

        @Override
        public int compare(Node n1, Node n2) {
            return Integer.compare(Integer.valueOf(n1.name()), Integer.valueOf(n2.name()));
        }
    }


//makes list of nodes with >=n neighbours
    private static Set<Node> searchForNodesWithXNeighbours(Graph graph, int numberOfNeighbours) throws IOException {

        List<Node> SortedNodeList = sortNodes(graph.nodes());
        Set<Node> newNodeList = new HashSet<Node>();

        for (Node n : SortedNodeList) {
            if (n.neighbours().size() >= numberOfNeighbours) {
                newNodeList.add(n);
            }
        }
        System.out.println("Number of nodes with at least " +  numberOfNeighbours + " neighbours: " + newNodeList.size());
        return newNodeList;
    }

    // returns set of fully connected nodes
    private static Set<Node> findFullyConnectedNeighbours(Graph graph) throws IOException {

        List<Node> sortedNodeList = sortNodes(graph.nodes());
        Set<Node> fullyConnectedNodes = new HashSet<Node>();
        int i = 0;

        for(Node n : sortedNodeList){
            if (nodeConnectedNeighbours(n,graph)) {
                fullyConnectedNodes.add(n);
                i++;
            }
        }
        System.out.println("number of nodes with fully connected neighbours: " + i);
        return fullyConnectedNodes;
    }

    private static boolean nodeConnectedNeighbours(Node n, Graph graph) throws IOException {

        List<Node> neighboursList = sortNodes(n.neighbours());

        for (Node node : neighboursList) {
            Node neighbour = graph.find(node.name());
            neighbour.removeNeighbour(n);
            n.removeNeighbour(node);
            if (subsetCheck(neighbour.neighbours(),n.neighbours()) == false){
                return false;
            }
        }
        return true;
    }

    private static boolean subsetCheck(Set<Node> setA, Set<Node> setB ){
        for (Node n : setB) {
            if (setA.contains(n) == false) {
                return false;
            }
        }
        return true;
    }

}




