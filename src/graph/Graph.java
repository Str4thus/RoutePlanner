package graph;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.XMLReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Graph {
    private Document source;
    private ArrayList<Node> nodes = new ArrayList<>();
    private HashMap<String, LinkedList<Edge>> edges = new HashMap<>(); // edges[nodeId] = sorted list of edges OUTGOING from the node
    private HashMap<String, Integer> nodeLookupMap = new HashMap<>();

    public Graph(String filename) {
        source = XMLReader.readXMLFile(filename);
    }

    public void loadNodes() {
        NodeList nodesFromFile = source.getElementsByTagName("node");

        for (int i = 0; i < nodesFromFile.getLength(); i++) {
            Element nodeElement = (Element) nodesFromFile.item(i);
            org.w3c.dom.NodeList nodeData = nodeElement.getElementsByTagName("data");

            Node newNode = new Node(
                    nodeElement.getAttribute("id"),
                    Float.parseFloat(nodeData.item(0).getTextContent()),
                    Float.parseFloat(nodeData.item(1).getTextContent())
            );

            nodeLookupMap.put(newNode.getId(), i);
            nodes.add(newNode);
        }
    }

    public void loadEdges() {
        NodeList edgesFromFile = source.getElementsByTagName("edge");

        for (int i = 0; i < edgesFromFile.getLength(); i++) {
            Element edgeElement = (Element) edgesFromFile.item(i);
            org.w3c.dom.NodeList edgeData = edgeElement.getElementsByTagName("data");

            Node nodeA = nodes.get(nodeLookupMap.get(edgeElement.getAttribute("source")));
            Node nodeB = nodes.get(nodeLookupMap.get(edgeElement.getAttribute("target")));

            Edge newEdge = new Edge(
                    edgeData.item(0).getTextContent(), // name
                    nodeA,
                    nodeB,
                    Float.parseFloat(edgeData.item(1).getTextContent()) // weight
            );

            edges.computeIfAbsent(nodeA.getId(), k -> new LinkedList<>());
            edges.get(nodeA.getId()).add(newEdge);
        }
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
}
