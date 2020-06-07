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

    public Route findShortestPath(String start, String end) {
        return graph.findShortestPath(start, end);
    }
}
