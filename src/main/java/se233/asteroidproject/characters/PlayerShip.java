package se233.asteroidproject.characters;

import se233.asteroidproject.characters.builds.Characters;
import se233.asteroidproject.main.AsteroidsGame;

import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

public class PlayerShip extends Characters {

    private double dragCoefficient = 0.01;
    private int remainingLives;
    public static int MAX_LIVES = 3;

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    private int remainHyperspaceJump;
    private boolean isUncollisionable = false;

    //get the player image from resource directory
    public PlayerShip() {
        super(new ImageView(new Image(PlayerShip.class.getResource("/Player.png").toExternalForm())), 380, 270);
        this.remainingLives = MAX_LIVES;
        this.remainHyperspaceJump = 3;
    }

    @Override
    public void move() {
        this.entityShape.setTranslateX(this.entityShape.getTranslateX() + this.movement.getX());
        this.entityShape.setTranslateY(this.entityShape.getTranslateY() + this.movement.getY());

        movement = movement.multiply(1 - dragCoefficient);

        if (this.entityShape.getTranslateX() < 0) {
            this.entityShape.setTranslateX(this.entityShape.getTranslateX() + AsteroidsGame.WIDTH);
        }

        if (this.entityShape.getTranslateX() > AsteroidsGame.WIDTH) {
            this.entityShape.setTranslateX(this.entityShape.getTranslateX() % AsteroidsGame.WIDTH);
        }

        if (this.entityShape.getTranslateY() < 0) {
            this.entityShape.setTranslateY(this.entityShape.getTranslateY() + AsteroidsGame.HEIGHT);
        }

        if (this.entityShape.getTranslateY() > AsteroidsGame.HEIGHT) {
            this.entityShape.setTranslateY(this.entityShape.getTranslateY() % AsteroidsGame.HEIGHT);
        }
    }

    public void applyThrust() {
        double changeX = Math.cos(Math.toRadians(this.entityShape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.entityShape.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }

    public void hyperspaceJump(List<Characters> enemies) {
        if (this.remainHyperspaceJump > 0) {
            boolean collided;
            do {
                setNewLocation();
                collided = false;
                for (Characters enemy : enemies) {
                    if (enemy.hasCollided(this)) {
                        collided = true;
                        break;
                    }
                }
            } while (collided);

            invisibility(Duration.seconds(3), Color.WHITE);
            this.remainHyperspaceJump -= 1;
        }
    }

    public void setNewLocation() {
        Random r = new Random();
        int hyperSpaceX = r.nextInt(AsteroidsGame.WIDTH - 50) + 50;
        int hyperSpaceY = r.nextInt(AsteroidsGame.HEIGHT - 50) + 50;

        this.entityShape.setTranslateX(hyperSpaceX);
        this.entityShape.setTranslateY(hyperSpaceY);
    }

    public void decreaseLife() {
        if (!isUncollisionable) {
            this.remainingLives--;
            dyingAnimation();
            invisibility(Duration.seconds(3), Color.TRANSPARENT);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                // back to og image
                this.entityShape.setImage(new Image(PlayerShip.class.getResource("/Player.png").toExternalForm()));
                entityShape.setTranslateX(AsteroidsGame.WIDTH / 2);
                entityShape.setTranslateY(AsteroidsGame.HEIGHT / 2);
                invisibility(Duration.seconds(1), Color.WHITE);
            });
            pause.play();
        }
    }

    public boolean getIsUncollisionable() {
        return isUncollisionable;
    }

    public void setIsUncollisionable(boolean value) {
        isUncollisionable = value;
    }

    private void invisibility(Duration duration, Color color) {
        isUncollisionable = true;
        Timeline timeline = new Timeline(new KeyFrame(duration, e -> {
            isUncollisionable = false;
        }));
        timeline.play();
    }

    public void dyingAnimation() {
        // Change the spaceship image to PlayerDeath.png
        this.entityShape.setImage(new Image(PlayerShip.class.getResource("/PlayerDeath.png").toExternalForm()));

        // Put the ship in the center when player dies
        double centerX = AsteroidsGame.WIDTH / 2;
        double centerY = AsteroidsGame.HEIGHT / 2;

        this.entityShape.setTranslateX(centerX);
        this.entityShape.setTranslateY(centerY);

        // Debug statement to verify the method is called
        System.out.println("Dying animation played, spaceship repositioned to center: " + centerX + ", " + centerY);
    }
}