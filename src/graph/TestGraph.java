package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TestGraph {
    private ArrayList<Node> nodes = new ArrayList<>();
    private HashMap<String, LinkedList<Edge>> edges = new HashMap<>(); // edges[nodeId] = sorted list of edges OUTGOING from the node
    private HashMap<String, Integer> nodeLookupMap = new HashMap<>();


    public TestGraph() {
        loadNodes();
        loadEdges();
    }

    private void loadNodes() {
        Node newNode;
        newNode = new Node(
                "A",
                0,
                0
        );nodeLookupMap.put(newNode.getId(), 0);
        nodes.add(newNode);

        newNode = new Node(
                "B",
                0,
                0
        );nodeLookupMap.put(newNode.getId(), 1);
        nodes.add(newNode);

        newNode = new Node(
                "C",
                0,
                0
        );nodeLookupMap.put(newNode.getId(), 2);
        nodes.add(newNode);

        newNode = new Node(
                "D",
                0,
                0
        );
        nodeLookupMap.put(newNode.getId(), 3);
        nodes.add(newNode);

        newNode = new Node(
                "E",
                0,
                0
        );
        nodeLookupMap.put(newNode.getId(), 4);
        nodes.add(newNode);

    }

    private void loadEdges() {
        Edge newEdge;
        newEdge = new Edge(
                "A->B",
                nodes.get(0),
                nodes.get(1),
                6
        );
        addEdge(nodes.get(0), newEdge);

        newEdge = new Edge(
                "A->D",
                nodes.get(0),
                nodes.get(3),
                1
        );
        addEdge(nodes.get(0), newEdge);

        newEdge = new Edge(
                "B->A",
                nodes.get(1),
                nodes.get(0),
                6
        );
        addEdge(nodes.get(1), newEdge);

        newEdge = new Edge(
                "B->C",
                nodes.get(1),
                nodes.get(2),
                5
        );
        addEdge(nodes.get(1), newEdge);

        newEdge = new Edge(
                "B->D",
                nodes.get(1),
                nodes.get(3),
                2
        );
        addEdge(nodes.get(1), newEdge);

        newEdge = new Edge(
                "B->E",
                nodes.get(1),
                nodes.get(4),
                2
        );
        addEdge(nodes.get(1), newEdge);

        newEdge = new Edge(
                "C->B",
                nodes.get(2),
                nodes.get(1),
                5
        );
        addEdge(nodes.get(2), newEdge);

        newEdge = new Edge(
                "C->E",
                nodes.get(2),
                nodes.get(4),
                5
        );
        addEdge(nodes.get(2), newEdge);

        newEdge = new Edge(
                "D->A",
                nodes.get(3),
                nodes.get(0),
                1
        );
        addEdge(nodes.get(3), newEdge);

        newEdge = new Edge(
                "D->B",
                nodes.get(3),
                nodes.get(1),
                2
        );
        addEdge(nodes.get(3), newEdge);

        newEdge = new Edge(
                "D->E",
                nodes.get(3),
                nodes.get(4),
                1
        );
        addEdge(nodes.get(3), newEdge);

        newEdge = new Edge(
                "E->B",
                nodes.get(4),
                nodes.get(1),
                2
        );
        addEdge(nodes.get(4), newEdge);

        newEdge = new Edge(
                "E->C",
                nodes.get(4),
                nodes.get(2),
                5
        );
        addEdge(nodes.get(4), newEdge);

        newEdge = new Edge(
                "E->D",
                nodes.get(4),
                nodes.get(3),
                1
        );
        addEdge(nodes.get(4), newEdge);
    }

    private void addEdge(Node nodeA, Edge e) {
        edges.computeIfAbsent(nodeA.getId(), k -> new LinkedList<>());
        edges.get(nodeA.getId()).add(e);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public HashMap<String, LinkedList<Edge>> getEdges() {
        return edges;
    }









    public void findShortestPath(String start, String end) {
        Node startNode = nodes.get(nodeLookupMap.get(start));
        Node endNode = nodes.get(nodeLookupMap.get(end));

        startNode.shortestDistanceFromStart = 0;

        scanNode(startNode, endNode);

        Node x = endNode.previousNode;
        while(x != null) {
            System.out.println(x.getId());
            x = x.previousNode;
        }

        System.out.println(endNode.shortestDistanceFromStart);
    }

    private void scanNode(Node node, Node target) {
        for (Edge e : edges.get(node.getId())) {
            if (!e.getB().wasVisited && (node.shortestDistanceFromStart + e.getWeight()) < e.getB().shortestDistanceFromStart) {
                e.getB().previousNode = node;
                e.getB().shortestDistanceFromStart = node.shortestDistanceFromStart + e.getWeight();
            }
        }

        node.wasVisited = true;

        for (Edge e : edges.get(node.getId())) {
            if (!e.getB().wasVisited && e.getB() != target)
                scanNode(e.getB(), target);
        }
    }
}
