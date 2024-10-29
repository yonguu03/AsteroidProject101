package se233.asteroidproject.main;

import se233.asteroidproject.controllers.SceneController;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AsteroidsGame extends Application {
    // this two variable are used to set all entities move on the screen.
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static SceneController sceneController;

    @Override
    public void start(Stage stage) throws Exception {
        // create the pane, all entities should in the pane
        Pane pane = new Pane();
        // set the size of the screen.
        pane.setPrefSize(WIDTH, HEIGHT);

        sceneController = new SceneController(pane, stage);
        sceneController.setHomePageScene();
        sceneController.showHomePage();

    }

    public static void main(String[] args) {
        try{
            launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
