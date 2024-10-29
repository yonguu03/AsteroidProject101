package se233.asteroidproject.controllers;

import se233.asteroidproject.characters.builds.Characters;
import se233.asteroidproject.menu.GeneralButton;
import se233.asteroidproject.characters.*;
import se233.asteroidproject.main.AsteroidsGame;
import static se233.asteroidproject.config.AppConstants.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GameController extends GeneralController {

    private static final Logger logger = Logger.getLogger(GameController.class.getName());

    private PlayerShip playerShip;
    private GameState gameState;
    private String highscore = "";
    private List<Bullet> bullets = new CopyOnWriteArrayList<>();
    private List<Bullet> alienBullets = new CopyOnWriteArrayList<>();
    private List<Asteroids> asteroids = new CopyOnWriteArrayList<>();
    private List<Boss> bosses = new CopyOnWriteArrayList<>();
    private AnimationTimer animationTimer;
    private AnimationTimer alienAnimation;
    private LevelController levelController;
    private Label scoreLabel;
    private Label levelLabel;
    private Label lifeLabel;
    private long lastAlienShot = 0;
    private Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
    private AtomicInteger score = new AtomicInteger(0); // Define and initialize score

    public GameController(Pane pane, Stage stage) {
        this.homePane = pane;
        this.pane = new Pane();
        this.pane.setPrefSize(AsteroidsGame.WIDTH, AsteroidsGame.HEIGHT);
        this.homeStage = stage;
        this.stage = new Stage();
        this.scene = setGameScene();
        this.stage.show();
        this.gameState = new GameState(); // Initialize GameState
    }

    private Scene setGameScene() {
        try {
            Image backgroundImage = new Image(getClass().getResource("/Background.png").toExternalForm());
            Background background = new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
            this.pane.setBackground(background);
        } catch (Exception e) {
            logger.severe("Failed to load background image: " + e.getMessage());
        }
        this.scene = new Scene(this.pane);
        this.stage.setTitle("Asteroids - Game");
        this.stage.setScene(this.scene);
        return this.scene;
    }

    public void startGame() {
        try {
            levelController = new LevelController();
            addPlayer();
            addCharacters();
            displayGameStats();
            displayMenuButton();

            Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
            this.scene.getRoot().requestFocus();
            this.scene.setOnKeyPressed(event -> {
                pressedKeys.put(event.getCode(), Boolean.TRUE);
            });

            this.scene.setOnKeyReleased(event -> {
                pressedKeys.put(event.getCode(), Boolean.FALSE);
            });

            this.animationTimer = new AnimationTimer() {
                @Override
                public void handle(long nanosec) {
                    handleKeyPressAction(pressedKeys);
                    updateCharactersOnScreen();
                }
            };
            this.animationTimer.start();

            this.alienAnimation = new AnimationTimer() {
                public void handle(long now) {
                    alienMovement(now);
                }
            };
            alienAnimation.start();
        } catch (Exception e) {
            logger.severe("Failed to start game: " + e.getMessage());
        }
    }
    public void stopGame() {
        this.animationTimer.stop();
        this.alienAnimation.stop();
        displayGameOver();
    }

    private void displayGameOver() {
        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setFont(new Font(100));
        gameOverLabel.setLayoutX(AsteroidsGame.WIDTH / 2 - 250);
        gameOverLabel.setLayoutY(AsteroidsGame.HEIGHT / 2 - 200);
        gameOverLabel.setTextFill(Color.WHITE);
        this.pane.getChildren().addAll(gameOverLabel);
    }

    private void addCharacters() {
        try {
            List<Characters> enemies = levelController.addEnemiesBasedOnLevel();
            enemies.forEach(enemy -> {
                if (enemy instanceof Asteroids) {
                    asteroids.add((Asteroids) enemy);
                } else if (enemy instanceof Boss) {
                    bosses.add((Boss) enemy);
                }
                this.pane.getChildren().add(enemy.getEntityShape());
            });
        } catch (Exception e) {
            logger.severe("Failed to add characters: " + e.getMessage());
        }
    }

    private void clearCharacters() {
        ObservableList<Node> children = this.pane.getChildren();
        children.removeIf(child -> child instanceof ImageView && child != this.playerShip.getEntityShape());
        this.asteroids.clear();
        this.bosses.clear();
        this.bullets.clear();
        this.alienBullets.clear();
    }

    private void updateCharactersOnScreen() {
        int[] enemyCounts = levelController.getEnemyCounts();

        if (this.asteroids.stream().filter(asteroid -> asteroid instanceof AsteroidsVar.SmallAsteroids).count() < enemyCounts[0]) {
            // Logic to add more small asteroids if needed
        }

        if (this.asteroids.stream().filter(asteroid -> asteroid instanceof AsteroidsVar.MediumAsteroids).count() < enemyCounts[1]) {
            // Logic to add more medium asteroids if needed
        }

        if (this.asteroids.stream().filter(asteroid -> asteroid instanceof AsteroidsVar.LargeAsteroids).count() < enemyCounts[2]) {
            // Logic to add more large asteroids if needed
        }

        if (this.bosses.size() < enemyCounts[3]) {
            // Logic to add more bosses if needed
        }
    }

    private void handleKeyPressAction(Map<KeyCode, Boolean> pressedKeys) {
        handlePlayerMovement(pressedKeys);
        handlePlayerShooting(pressedKeys);
        updateGameEntities();
    }

    private void handlePlayerMovement(Map<KeyCode, Boolean> pressedKeys) {
        if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
            this.playerShip.turnLeft();
        }
        if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
            this.playerShip.turnRight();
        }
        if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
            this.playerShip.applyThrust();
        }
    }

    private void handlePlayerShooting(Map<KeyCode, Boolean> pressedKeys) {
        if (pressedKeys.getOrDefault(KeyCode.SPACE, false)) {
            if (playerShip.getIsUncollisionable() || this.bullets.size() >= MAX_BULLET_FREQUENCY) {
                return;
            }
            shootBullet();
            pressedKeys.remove(KeyCode.SPACE);
        }
    }

    private void shootBullet() {
        try {
            double shipWidth = playerShip.getEntityShape().getBoundsInLocal().getWidth();
            double shipHeight = playerShip.getEntityShape().getBoundsInLocal().getHeight();
            Bullet bullet = new Bullet((int) playerShip.getEntityShape().getTranslateX(), (int) playerShip.getEntityShape().getTranslateY(), shipWidth, shipHeight);
            bullet.getEntityShape().setRotate(playerShip.getEntityShape().getRotate());
            bullets.add(bullet);
            bullet.move();
            pane.getChildren().add(bullet.getEntityShape());
        } catch (Exception e) {
            logger.severe("Failed to shoot bullet: " + e.getMessage());
        }
    }

    private void updateGameEntities() {
        this.playerShip.move();
        bullets.forEach(Bullet::move);
        asteroids.forEach(Asteroids::move);
        updateScore();
        asteroids.forEach(asteroid -> asteroid.handleCollision(bullets, asteroids, playerShip, pane));
        removeExpiredBullets();
    }

    private void removeExpiredBullets() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            if (System.currentTimeMillis() - bullet.getCreatedTime() > BULLET_LIFE_TIME) {
                bulletsToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletsToRemove);
        pane.getChildren().removeAll(bulletsToRemove.stream().map(Bullet::getEntityShape).collect(Collectors.toList()));
    }

    public void alienMovement(long now) {
        if (now - lastAlienShot >= 3_000_000_000L) {
            for (Boss boss : this.bosses) {
                if (isOnScreen(boss)) {
                    lastAlienShot = now;
                    double alienWidth = boss.getEntityShape().getBoundsInLocal().getWidth();
                    double alienHeight = boss.getEntityShape().getBoundsInLocal().getHeight();
                    Bullet bullet = new Bullet((int) boss.getEntityShape().getTranslateX(),
                            (int) boss.getEntityShape().getTranslateY(),
                            alienWidth, alienHeight);

                    if (playerShip.getEntityShape().getTranslateX() == boss.getEntityShape().getTranslateX()) {
                        bullet.getEntityShape().setRotate(90);
                    } else if (playerShip.getEntityShape().getTranslateX() > boss.getEntityShape().getTranslateX()) {
                        bullet.getEntityShape().setRotate(Math.atan((playerShip.getEntityShape().getTranslateY()
                                - boss.getEntityShape().getTranslateY())
                                / (playerShip.getEntityShape().getTranslateX() - boss.getEntityShape().getTranslateX()))
                                * 180 / Math.PI);
                    } else if (playerShip.getEntityShape().getTranslateX() < boss.getEntityShape().getTranslateX()) {
                        bullet.getEntityShape().setRotate(Math.atan((playerShip.getEntityShape().getTranslateY()
                                - boss.getEntityShape().getTranslateY())
                                / (playerShip.getEntityShape().getTranslateX() - boss.getEntityShape().getTranslateX()))
                                * 180 / Math.PI + 180);
                    }

                    alienBullets.add(bullet);
                    this.pane.getChildren().add(bullet.getEntityShape());
                }
            }
        }

        alienBullets.forEach(shot -> shot.move());
        alienBullets.removeIf(shot -> !isOnScreen(shot));
        bosses.forEach(obj -> obj.move());
    }

    private void updateScore() {
        bullets.forEach(bullet -> {
            for (Asteroids asteroid : asteroids) {
                if (asteroid.hasCollided(bullet)) {
                    if (asteroid instanceof AsteroidsVar.LargeAsteroids) {
                        int newScore = score.addAndGet(Points.LARGE_ASTEROID.getPoint());
                        levelLabel.setText("Level: " + levelController.getLevel());
                        logger.info("Large asteroid destroyed, new score: " + newScore);
                    } else if (asteroid instanceof AsteroidsVar.MediumAsteroids) {
                        int newScore = score.addAndGet(Points.MEDIUM_ASTEROID.getPoint());
                        this.scoreLabel.setText("Score: " + newScore);
                        logger.info("Medium asteroid destroyed, new score: " + newScore);
                    } else {
                        int newScore = score.addAndGet(Points.SMALL_ASTEROID.getPoint());
                        this.scoreLabel.setText("Score: " + newScore);
                        logger.info("Small asteroid destroyed, score updated to " + newScore);
                    }
                    updateLevel();
                }
            }
            for (Boss boss : bosses) {
                if (bullet.hasCollided(boss)) {
                    int newScore = score.getAndAdd(Points.ALIEN.getPoint());
                    this.scoreLabel.setText("Score: " + newScore);
                    logger.warning( "Boss destroyed, new score: " + newScore);
                    updateLevel();
                }
            }
        });
        handlePlayerCollisions();
    }

    private void handlePlayerCollisions() {
        asteroids.forEach(asteroid -> {
            if (asteroid.hasCollided(this.playerShip)) {
                playerShip.decreaseLife();
                gameState.setPlayerHealth(playerShip.getRemainingLives());
                this.lifeLabel.setText("Life:" + gameState.getPlayerHealth());
                if (gameState.getPlayerHealth() <= 0) {
                    checkScore();
                }
            }
        });
        alienBullets.forEach(bullet -> {
            if (bullet.hasCollided(this.playerShip)) {
                playerShip.decreaseLife();
                gameState.setPlayerHealth(playerShip.getRemainingLives());
                this.lifeLabel.setText("Life:" + gameState.getPlayerHealth());
                if (gameState.getPlayerHealth() <= 0) {
                    checkScore();
                }
            }
        });
    }

    private void updateLevel() {
        int newLevel = levelController.newLevel(score.get());
        if (newLevel != levelController.getLevel()) {
            levelController.setLevel(newLevel);
            this.levelLabel.setText("Level:" + levelController.getLevel());
            clearCharacters();
            addCharacters();
        }
    }

    private PlayerShip addPlayer() {
        this.playerShip = new PlayerShip();
        this.pane.getChildren().add(playerShip.getEntityShape());
        return this.playerShip;
    }

    private List<Characters> groupEnemies() {
        List<Characters> enemies = new ArrayList<>();
        enemies.addAll(asteroids);
        enemies.addAll(bullets);
        enemies.addAll(bosses);
        return enemies;
    }

    private void checkScore() {
        stopGame();
        highscore = "Anon:0";
        String[] highscoreParts = highscore.split(":");
        if (highscoreParts.length >= 2 && score.get() > Integer.parseInt(highscoreParts[1])) {
            Label gameOverLabel = new Label("Game Over!");
            gameOverLabel.setFont(new Font(100));
            gameOverLabel.setLayoutX(AsteroidsGame.WIDTH / 2 - 250);
            gameOverLabel.setLayoutY(AsteroidsGame.HEIGHT / 2 - 200);
            gameOverLabel.setTextFill(Color.WHITE);

            Label PromptText = new Label("Please Enter your name: ");
            PromptText.setFont(new Font(30));
            PromptText.setLayoutX(AsteroidsGame.WIDTH / 2 - 150);
            PromptText.setLayoutY(AsteroidsGame.HEIGHT / 2);
            PromptText.setTextFill(Color.WHITE);

            TextField nameInput = new TextField();
            nameInput.setFont(new Font(20));
            nameInput.setPromptText("Please Enter your name");
            nameInput.setLayoutX(AsteroidsGame.WIDTH / 2 - 200);
            nameInput.setLayoutY(AsteroidsGame.HEIGHT / 2 + 60);

            GeneralButton submitButton = new GeneralButton("Save");

            submitButton.setLayoutX(AsteroidsGame.WIDTH / 2 + 60);
            submitButton.setLayoutY(AsteroidsGame.HEIGHT / 2 + 60);

            submitButton.setOnAction(e -> {
                String playerName = nameInput.getText();
                String newHighScore = playerName + ":" + score.get();
                highscore = newHighScore;
                System.out.println("PlayerShip name: " + playerName);
                saveHighScore();
                AsteroidsGame.sceneController.toggleStageView(stage, homeStage);
            });

            this.pane.getChildren().addAll(gameOverLabel, PromptText, nameInput, submitButton);

        } else {
            stopGame();
        }
    }

    private void saveHighScore() {
        File scoreFile = new File("highscore.dat");
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                logger.severe("Failed to create highscore file: " + e.getMessage());
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile, true))) {
            writer.write(highscore);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            logger.severe("Failed to save highscore: " + e.getMessage());
        }
    }
    public void resetHighScore() {
        try {
            File scoreFile = new File("highscore.dat");
            if (scoreFile.exists()) {
                scoreFile.delete();
                logger.severe("Scores have been reset");

            }
            scoreFile.createNewFile();
            AsteroidsGame.sceneController.toggleStageView(stage, homeStage);
        } catch (IOException e) {
            logger.severe("Failed to reset highscore: " + e.getMessage());
        }
    }

    private boolean isOnScreen(Characters ge) {
        if (ge.getEntityShape().getTranslateX() < AsteroidsGame.WIDTH
                && ge.getEntityShape().getTranslateY() < AsteroidsGame.HEIGHT) {
            return true;
        } else {
            return false;
        }
    }

    private void displayGameStats() {
        int gameStatBegin_X = 20;
        int gameStatBegin_Y = 20;
        scoreLabel = displayScore(gameStatBegin_X, gameStatBegin_Y);
        levelLabel = displayLevel(gameStatBegin_X, scoreLabel.getTranslateY() + 40);
        lifeLabel = displayLife(gameStatBegin_X, levelLabel.getTranslateY() + 40);
        this.pane.getChildren().addAll(scoreLabel, levelLabel, lifeLabel);
    }

    private Label displayScore(double position_x, double position_y) {
        Label scoreLabel = new Label("Score: " + gameState.getScore());
        scoreLabel.setTranslateX(position_x);
        scoreLabel.setTranslateY(position_y);
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font(30));
        scoreLabel.setVisible(true);
        return scoreLabel;
    }

    private Label displayLevel(double position_x, double position_y) {
        Label levelLabel = new Label("Level: " + this.levelController.getLevel());
        levelLabel.setTranslateX(position_x);
        levelLabel.setTranslateY(position_y);
        levelLabel.setTextFill(Color.WHITE);
        levelLabel.setFont(Font.font(30));
        levelLabel.setVisible(true);
        return levelLabel;
    }

    private Label displayLife(double position_x, double position_y) {
        Label lifeLabel = new Label("Life: " + this.playerShip.getRemainingLives());
        lifeLabel.setTranslateX(position_x);
        lifeLabel.setTranslateY(position_y);
        lifeLabel.setTextFill(Color.WHITE);
        lifeLabel.setFont(Font.font(30));
        lifeLabel.setVisible(true);
        return lifeLabel;
    }
}

