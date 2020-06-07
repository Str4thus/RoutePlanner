package graph;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.XMLReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Graph {
    private Document source;
    private ArrayList<Node> nodes = new ArrayList<>();
    private HashMap<String, LinkedList<Edge>> edges = new HashMap<>(); // edges[nodeId] = sorted list of edges OUTGOING from the node
    private HashMap<String, Integer> nodeLookupMap = new HashMap<>();
    private Route lastRoute;

    public Graph(String filename) {
        source = XMLReader.readXMLFile(filename);
        init();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public HashMap<String, LinkedList<Edge>> getEdges() {
        return edges;
    }

    public void exportRoute(String filename) {
        if (lastRoute == null)
            return;

        try {
            FileWriter fileWriter = new FileWriter(filename + ".txt");
            fileWriter.write(lastRoute.toString());
            fileWriter.close();
            System.out.println("Wrote file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Route findShortestPath(String start, String end) {
        for (Node node : nodes) {
            node.resetForPathFinding();
        }

        Node startNode = nodes.get(nodeLookupMap.get(start));
        Node endNode = nodes.get(nodeLookupMap.get(end));

        startNode.shortestDistanceFromStart = 0;

        scanNode(startNode, endNode);

        lastRoute = new Route(endNode);
        return lastRoute;
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


    private void init() {
        loadNodes();
        loadEdges();
    }

    private void loadNodes() {
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

    private void loadEdges() {
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

            nodeA.addEdge(newEdge);
        }
    }
}
