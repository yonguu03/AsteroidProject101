package se233.asteroidproject.controllers;

import se233.asteroidproject.characters.builds.Characters;
import se233.asteroidproject.main.AsteroidsGame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import se233.asteroidproject.menu.GameButton;

import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SceneController extends se233.asteroidproject.controllers.GeneralController {
    private SceneController sceneController;

    List<GameButton> menuButtons;

    private int MENU_BUTTON_START_X = AsteroidsGame.WIDTH/2;
    private int MENU_BUTTON_START_Y = AsteroidsGame.HEIGHT/3;

    public SceneController(Pane pane, Stage stage) {
        this.pane = pane;
        this.stage = stage;
        sceneController = this;
    }

    public void toggleStageView(Stage hideStage, Stage showStage){
        hideStage.hide();
        showStage.show();
    }

    public Scene setHomePageScene(){
        this.pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        this.scene = new Scene(this.pane);
        this.stage.setTitle("Asteroids");
        this.stage.setScene(this.scene);
        this.scene.setFill(Color.DARKBLUE);
        return this.scene;
    }


    private void AddMenuButtons(GameButton button) {
        button.setLayoutX(MENU_BUTTON_START_X);
        if(menuButtons != null && menuButtons.size() > 0){
            button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
        } else{
            button.setLayoutY(MENU_BUTTON_START_Y);
        }
        menuButtons.add(button);
        this.pane.getChildren().add(button);
    }

    public void showHomePage(){
        menuButtons = new ArrayList<>();
        displayGameTitle();
        createButtons();
        this.stage.show();
    }

    private void displayGameTitle(){
        InnerShadow is = new InnerShadow();
        is.setOffsetX(4.0f);
        is.setOffsetY(4.0f);
        // little title thing above the play, highscore, etc button
        Text t = new Text();
        t.setEffect(is);
        t.setX(AsteroidsGame.WIDTH/2 -150);
        t.setY(100);
        t.setText("Asteroids");
        t.setFill(Color.WHITE);
        t.setFont(Font.font(null, FontWeight.LIGHT, 80));

        this.pane.getChildren().add(t);
    }
    private void createButtons() {
        createStartButton();
        createScoresButton();
        createResetScoresButton();
        createExitButton();
    }

    private void createStartButton(){
        GameButton startButton = new GameButton("PLAY");
        AddMenuButtons(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                se233.asteroidproject.controllers.GameController gameController = new se233.asteroidproject.controllers.GameController(pane, stage);
                gameController.startGame();
            }
        });
    }

    private void createScoresButton(){
        GameButton scoreButton = new GameButton("HIGH SCORES");
        AddMenuButtons(scoreButton);

        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ScorecardController scorecardController = new ScorecardController(pane, stage);
                scorecardController.displayScoreCard();
                scorecardController.displayMenuButton();
            }
        });
    }

    private void createResetScoresButton(){
        GameButton scoreButton = new GameButton("SCORE RESET");
        AddMenuButtons(scoreButton);

        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                se233.asteroidproject.controllers.GameController gameController = new se233.asteroidproject.controllers.GameController(pane, stage);
                gameController.resetHighScore();
            }
        });
    }


    private void createExitButton(){
        GameButton exitButton = new GameButton("EXIT");
        AddMenuButtons(exitButton);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
    }

    public void removeEntity(Characters entity){

    }
}
