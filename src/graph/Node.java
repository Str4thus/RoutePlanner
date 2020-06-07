package graph;

import java.util.LinkedList;

// X -> LON
// Y -> LAT
public class Node {
    public boolean wasVisited = false; // TODO MAKE PRIVATE
    public Node previousNode = null;
    public float shortestDistanceFromStart = Float.MAX_VALUE;


    private String id;
    private float lat, lon;
    private float xpos, ypos;

    private LinkedList<Edge> edges = new LinkedList<>();
    public Node(String id, float lat, float lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;

        // TODO clean up
        float pixelPerLon = Math.abs((225 - 195) / (10.9602165f - 10.1461712f));
        float pixelPerLat = Math.abs((50 - 100) / (54.3584108f - 53.5415266f));

        this.xpos = 225 + (( 10.9602165f- this.lon) * -pixelPerLon);
        this.ypos = 50 + (( 54.3584108f- this.lat) * pixelPerLat);
    }

    public float getXpos() {
        return xpos;
    }

    public float getYpos() {
        return ypos;
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

    public Edge getEdgeTo(String id) {
        for (Edge edge : edges) {
            if (edge.getB().getId().equals(id)) {
                return edge;
            }
        }

        return null;
    }

    public Edge getEdgeTo(Node node) {
        for (Edge edge : edges) {
            if (edge.getB().getId().equals(node.id)) {
                return edge;
            }
        }

        return null;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        edges.sort((e1, e2) -> Float.compare(e1.getWeight(), e2.getWeight()));
    }
}
