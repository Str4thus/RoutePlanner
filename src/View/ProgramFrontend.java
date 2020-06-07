package view;

import controller.MyController;
import graph.Edge;
import graph.Node;
import graph.Route;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProgramFrontend {
    private Scene menuScene;
    private Scene mainScene;
    private Scene listScene;

    private BorderPane menuRoot;
    private HBox newRouteRoot;
    private VBox routeListRoot;

    private Stage primaryStage;
    private String font = "Comic Sans MS";

    private MyController controller;

    public void init(Stage primaryStage, MyController myController)  {
        this.primaryStage = primaryStage;
        this.controller = myController;
    }

    public void setPrimaryStage(){
        primaryStage.setTitle("Routenplaner");
        primaryStage.setScene(menuScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public void allRoots() {
        menuRoot = new BorderPane();
        newRouteRoot = new HBox();
        routeListRoot = new VBox();

        //All Scenes
        menuScene = new Scene(menuRoot, 900, 600);
        mainScene = new Scene(newRouteRoot, 900, 600);
        listScene = new Scene(routeListRoot, 900, 600);
    }

    public void mainMenu() {
        VBox menuButtons = new VBox();
        menuButtons.setSpacing(20);

        Button newRouteButton = new Button("Neue Route");
        Button routeListButton = new Button("Routenliste");
        Button exitButton = new Button("Exit");

        newRouteButton.setMinSize(300, 50);
        routeListButton.setMinSize(300, 50);
        exitButton.setMinSize(300, 50);


        newRouteButton.setFont(Font.font(font, 30));
        routeListButton.setFont(Font.font(font, 30));
        exitButton.setFont(Font.font(font, 30));

        newRouteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newRouteGeneral();
                primaryStage.setScene(mainScene);
            }
        });

        routeListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(listScene);
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        menuButtons.getChildren().addAll(newRouteButton, routeListButton, exitButton);
        menuRoot.setCenter(menuButtons);
        menuButtons.setAlignment(Pos.CENTER);
    }


    public void newRouteGeneral() {
        newRouteRoot.getChildren().clear();

        newRouteRoot.setSpacing(70);
        newRouteRoot.setPadding(new Insets(30));

        Button exportButton = new Button("Exportieren");
        Button saveButton = new Button("Speichern");
        Button calculateRouteButton = new Button("Route Berechnen");
        Button nRouteBackButton = new Button("<-");
        nRouteBackButton.setOnAction((event) -> {
            primaryStage.setScene(menuScene);
        });
        nRouteBackButton.setMinSize(55,55);
        nRouteBackButton.setFont(Font.font(20));

        Label enterStart = new Label("Start:");
        Label enterEnd = new Label("Ziel:");
        Label routeName = new Label("Routenname:");

        TextField startTextField = new TextField();
        TextField endTextField = new TextField();
        TextField routeNameInput = new TextField();
        TextArea bigRouteOutput = new TextArea();
        bigRouteOutput.setMinSize(60, 300);


        calculateRouteButton.setFont(Font.font(font, 25));
        exportButton.setFont(Font.font(font, 20));
        saveButton.setFont(Font.font(font, 20));

        enterStart.setFont(Font.font(font, 20));
        enterEnd.setFont(Font.font(font, 20));
        routeName.setFont(Font.font(font, 20));


        VBox leftMainVBox = new VBox(35);
        HBox hBoxLeftSide1 = new HBox();
        VBox vBoxInside1 = new VBox();
        VBox vBoxInside2 = new VBox(10);
        HBox startHBox = new HBox(5);
        HBox endHBox = new HBox(5);
        VBox vBoxMainLeft = new VBox(20);

        HBox hBoxLeftSide2 = new HBox(30);
        hBoxLeftSide2.getChildren().addAll(nRouteBackButton, calculateRouteButton);

        HBox routeNameHBox = new HBox(5);
        routeNameHBox.getChildren().addAll(routeName, routeNameInput);

        startHBox.getChildren().addAll(enterStart, startTextField);
        endHBox.getChildren().addAll(enterEnd, endTextField);
        vBoxInside2.getChildren().addAll(startHBox, endHBox);
        vBoxInside1.getChildren().addAll(vBoxInside2);
        hBoxLeftSide1.getChildren().addAll(vBoxInside1);
        vBoxMainLeft.getChildren().addAll(hBoxLeftSide1, bigRouteOutput);
        leftMainVBox.getChildren().addAll(routeNameHBox, vBoxMainLeft, hBoxLeftSide2);



        // Image Stuff
        Image mapImage = new Image("map.png", 500, 500, true, false); // gets resized to (w,h) = (422,500)
        Canvas canvas = new Canvas(mapImage.getWidth(), mapImage.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(mapImage, 0, 0, canvas.getWidth(), canvas.getHeight());

        /*
        example for a diagonal through the image:
            gc.strokeLine(0,0, 422, 500);

            here goes the code for drawing the nodes onto the canvas
         */


        for (Node node : controller.getNodes()) {
            gc.fillOval(node.getXpos(), node.getYpos(), 2, 2);

            for (Edge edge : node.getEdges()) {
                gc.strokeLine(node.getXpos(), node.getYpos(), edge.getB().getXpos(), edge.getB().getYpos());
            }
        }
/*
        //Route route = controller.findShortestPath("HEILIGENHAFEN - MITTE (A 1)", "LENSAHN (A 1)");
        //Route route = controller.findShortestPath("KREUZ DÜSSELDORF - NORD  (Südteil)", "BERLIN - KAISERDAMM (A 100)");
        //Route route = controller.findShortestPath("HEILIGENHAFEN - MITTE (A 1)", "OBERHACHING (A 995)");

        gc.drawImage(mapImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Paint.valueOf("red"));
        gc.setStroke(Paint.valueOf("red"));
        gc.setLineWidth(5d);
        for (int i = 0; i < route.size(); i++) {
            Node currentNode = route.get(i);
            gc.fillOval(currentNode.getXpos(), currentNode.getYpos(), 5, 5);

            if (i < route.size()-1) {
                Edge edge = route.get(i).getEdgeTo(route.get(i + 1));
                gc.strokeLine(currentNode.getXpos(), currentNode.getYpos(), edge.getB().getXpos(), edge.getB().getYpos());
            }
        }
        gc.setFill(Paint.valueOf("black"));
        gc.setStroke(Paint.valueOf("black"));
        gc.setLineWidth(2d);*/

        VBox rightMainVBox = new VBox(20);
        HBox saveExportHBox = new HBox(10);
        saveExportHBox.setAlignment(Pos.BOTTOM_RIGHT);
        saveExportHBox.getChildren().addAll(exportButton, saveButton);
        rightMainVBox.getChildren().addAll(canvas, saveExportHBox);



        newRouteRoot.getChildren().addAll(leftMainVBox, rightMainVBox);
    }


    public void routeListGeneral() {
        routeListRoot.setPadding(new Insets(30));

        Button rListBackButton = new Button("<-");
        rListBackButton.setOnAction((event) -> {
            primaryStage.setScene(menuScene);
        });
        rListBackButton.setMinSize(55,55);
        rListBackButton.setFont(Font.font(20));

        Label rankingTitle = new Label("Liste der gespeicherten Routen");
        rankingTitle.setFont(Font.font(font, 30));

        VBox routeList = new VBox();
        routeList.setAlignment(Pos.CENTER);

        routeList.getChildren().addAll(rankingTitle);
        routeListRoot.getChildren().addAll(rListBackButton, routeList);
    }
}
