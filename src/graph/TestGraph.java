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

    public void foo() {
        Node testNodeA = nodes.get(0);
        Node testNodeB = nodes.get(1);
        Node testNodeC = nodes.get(2);

        Edge edge1 = new Edge("A", testNodeA, testNodeB, 3);
        Edge edge2 = new Edge("B", testNodeA, testNodeC, 2);
        Edge edge3 = new Edge("C", testNodeA, testNodeC, 2.4f);
        Edge edge4 = new Edge("D", testNodeA, testNodeC, 19);
        Edge edge5 = new Edge("E", testNodeA, testNodeC, 1);

        testNodeA.addEdge(edge1);
        testNodeA.addEdge(edge2);
        testNodeA.addEdge(edge3);
        testNodeA.addEdge(edge4);
        testNodeA.addEdge(edge5);


        for (Edge e : testNodeA.getEdges()) {
            System.out.println(e.getWeight());
        }
    }

    public void foo2() {
        Node testNode = nodes.get(3);

        for (Edge edge : edges.get(testNode.getId())) {
            System.out.println(edge.getName() + ": " + edge.getA().getId() + " -> " + edge.getB().getId() + " (" + edge.getWeight() + ")");
        }
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
