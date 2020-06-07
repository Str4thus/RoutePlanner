package graph;

public class Edge {
    private String name;
    private Node a, b;
    private float weight;

    public Edge(String name, Node a, Node b, float weight) {
        this.name = name;
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public Node getA() {
        return a;
    }

    public Node getB() {
        return b;
    }

    public float getWeight() {
        return weight;
    }
}
