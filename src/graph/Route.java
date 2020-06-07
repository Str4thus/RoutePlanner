package graph;

import java.util.ArrayList;

public class Route extends ArrayList<Node> {

    public Route(Node endNode) {
        Node currentNode = endNode;

        while(currentNode != null) {
            this.add(0, currentNode);
            currentNode = currentNode.previousNode;
        }
    }

    @Override
    public String toString() {
        StringBuilder routeString = new StringBuilder();
        for (int i = 0; i < this.size() - 1; i++) { // skip last
            routeString.append(this.get(i).getId());
            routeString.append(" => ");
            routeString.append(this.get(i+1).getId());
            routeString.append("\n");
        }

        return routeString.toString();
    }
}
