package se233.asteroidproject.controllers;

import se233.asteroidproject.menu.GeneralButton;
import se233.asteroidproject.main.AsteroidsGame;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A GeneralController class that is intended to provide control across all functionalities of the game.
 */
public class GeneralController {
    /**
     * The pane object represents the current display area of the game's screen where game components are displayed.
     */
    public Pane pane;
    /**
     * The homePane object represents the main display area of the game's home screen where menu buttons are displayed.
     */
    public Pane homePane;

    /**
     * The stage object represents the main window of the game where the pane object is displayed.
     */
    public Stage stage;
    /**
     * The homeStage object represents the main window of the game's home screen where the homePane object is displayed.
     */
    public Stage homeStage;
    /**
     * The scene object represents the graphical user interface of the game.
     */
    public Scene scene;

    /**
     * The displayMenuButton method displays a menu button in the game's main display area.
     */
    public void displayMenuButton(){
        GeneralButton mainMenuButton = new GeneralButton("Main Menu");
        mainMenuButton.setLayoutX(AsteroidsGame.WIDTH - 170);
        mainMenuButton.setLayoutY(20);
        this.pane.getChildren().add(mainMenuButton);
        mainMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AsteroidsGame.sceneController.toggleStageView(stage, homeStage);
//                stage.close();
            }
        });
    }
}
