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

}
