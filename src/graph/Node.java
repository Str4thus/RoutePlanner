package graph;

import java.util.LinkedList;

public class Node {
    private String id;
    private float lat, lon;
    private LinkedList<Edge> edges = new LinkedList<>();

    public boolean wasVisited = false; // TODO MAKE PRIVATE
    public Node previousNode = null;
    public float shortestDistanceFromStart = Float.MAX_VALUE;

    public Node(String id, float lat, float lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        edges.sort((e1, e2) -> Float.compare(e1.getWeight(), e2.getWeight()));
    }
}
