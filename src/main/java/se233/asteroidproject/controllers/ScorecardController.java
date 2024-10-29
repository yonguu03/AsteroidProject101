package se233.asteroidproject.controllers;

import se233.asteroidproject.characters.builds.GameScore;
import se233.asteroidproject.main.AsteroidsGame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScorecardController extends se233.asteroidproject.controllers.GeneralController {

    public ScorecardController(Pane pane, Stage stage) {
        this.homePane = pane;
        this.pane = new Pane();
        this.pane.setPrefSize(AsteroidsGame.WIDTH, AsteroidsGame.HEIGHT);
        this.homeStage = stage;
        this.homeStage.hide();
        this.stage = new Stage();
        this.scene = setGameScene();
        this.stage.show();
    }

    // for the score page
    public Scene setGameScene(){
        this.pane.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
        this.scene = new Scene(this.pane);
        this.stage.setTitle("Asteroids - Score Card");
        this.stage.setScene(this.scene);
        this.scene.setFill(Color.GREY);
        return this.scene;
    }

    public void displayScoreCard(){
        //Creating a table view
        TableView table = new TableView();
        table.setTranslateX(50);
        table.setTranslateY(100);

//        table.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(1024), null)));
        TableColumn nameCol = new TableColumn("Name");
        TableColumn scoreCol = new TableColumn("Score");
        table.getColumns().addAll(nameCol, scoreCol);

        nameCol.setPrefWidth(400);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setStyle("-fx-font-weight:bold;-fx-font-size:20px;-fx-background-color:#cacbcc");
        scoreCol.setPrefWidth(400);
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setStyle("-fx-font-weight:bold;-fx-font-size:20px;-fx-alignment: CENTER;-fx-background-color:#cacbcc");
        scoreCol.setSortable(true);


        //Adding data to the table
        ObservableList<String> list = FXCollections.observableArrayList();
        table.setItems(getScoreData());

        this.pane.getChildren().addAll(table);
    }

    // highscore
    public List<GameScore> getScoreCard(){
        // format: Marc:100
        // same directory as class because no slash directory under
        List<GameScore> gameScores = new ArrayList<>();
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);
            String highScoreLine = "";
            while((highScoreLine = reader.readLine()) != null){
                if (highScoreLine.isEmpty()){
                    break;
                }
                String name = highScoreLine.split(":")[0];
                int score = Integer.parseInt(highScoreLine.split(":")[1]);
                GameScore gameScore = new GameScore(name, score);
                gameScores.add(gameScore);
            }

            return gameScores;
        }
        catch (Exception e) {
            return null;
        }
        finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ObservableList<GameScore> getScoreData() {
        ObservableList<GameScore> gameScores = FXCollections.observableArrayList();
        List<GameScore> scores = getScoreCard();
        if(scores != null && scores.size()> 0){
            Collections.sort(scores);
        } else{
            scores = new ArrayList<>();
            scores.add(new GameScore("-", 0));
        }

        gameScores.addAll(scores);
        return  gameScores;
    }
}
