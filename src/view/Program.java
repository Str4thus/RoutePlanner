package view;

import javafx.application.Application;
import javafx.stage.Stage;
import controller.MyController;

public class Program extends Application {
    private ProgramFrontend programFrontend;
    private MyController myController;

    public Program() {
        programFrontend = new ProgramFrontend();
        myController = new MyController(programFrontend);
    }

    public void startGui(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        programFrontend.init(primaryStage, myController);
        programFrontend.allRoots();
        programFrontend.mainMenu();
        programFrontend.newRouteGeneral();
        programFrontend.routeListGeneral();
        programFrontend.setPrimaryStage();
    }
}