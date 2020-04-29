package main;

import graph.Edge;
import graph.Graph;
import graph.TestGraph;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        /*Graph g = new Graph("data.xml");
        g.loadNodes();
        g.loadEdges();
        g.foo2();*/

        TestGraph testGraph = new TestGraph();

        testGraph.findShortestPath("C", "D");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
