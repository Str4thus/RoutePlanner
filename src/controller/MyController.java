package controller;

import graph.Graph;
import graph.Node;
import graph.Route;
import view.ProgramFrontend;

import java.util.ArrayList;

public class MyController{
    private ProgramFrontend programFrontend;
    private Graph graph;

    public MyController(ProgramFrontend programFrontend){
        this.programFrontend = programFrontend;
        this.graph = new Graph("data.xml");
    }

    public ArrayList<Node> getNodes() {
        return graph.getNodes();
    }

    public ArrayList<String> getNodeIds() {
        ArrayList<String> ids = new ArrayList<>();

        for (Node node : graph.getNodes()) {
            ids.add(node.getId());
        }

        return ids;
    }

    public Route findShortestPath(String start, String end) {
        if (start == null || end == null)
            return null;

        return graph.findShortestPath(start, end);
    }

    public void exportRoute(String filename) {
        graph.exportRoute(filename);
    }
}
