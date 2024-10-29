package se233.asteroidproject.characters.builds;

import javafx.scene.image.ImageView;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import se233.asteroidproject.interfaces.Moveable;
import se233.asteroidproject.main.AsteroidsGame;

import java.util.logging.Logger;

public abstract class Characters implements Moveable {
    private static final Logger logger = Logger.getLogger(Characters.class.getName());

    public ImageView entityShape;
    public Point2D movement;
    public double velocityX;
    public double velocityY;
    public Node view;
    public Boolean alive = true;

    public Characters(ImageView imageView, double x, double y) {
        this.entityShape = imageView;
        this.entityShape.setTranslateX(x);
        this.entityShape.setTranslateY(y);
        this.movement = new Point2D(0, 0);
    }

    public ImageView getEntityShape() {
        return entityShape;
    }

    public void setVelocity(double x, double y) {
        this.velocityX = x;
        this.velocityY = y;
        logger.info("Velocity set to: " + this.velocityX + ", " + this.velocityY);
    }

    public void move() {
        double changeX = Math.cos(Math.toRadians(this.entityShape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.entityShape.getRotate()));
        entityShape.setTranslateX(entityShape.getTranslateX() + changeX);
        entityShape.setTranslateY(entityShape.getTranslateY() + changeY);
        setEntityWithinScene();
    }

    public void setEntityWithinScene() {
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

    public boolean hasCollided(Characters other) {
        return this.entityShape.getBoundsInParent().intersects(other.getEntityShape().getBoundsInParent());
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isDead() {
        return !alive;
    }

    public String getType(Characters entity) {
        return entity.getClass().getSimpleName();
    }

    public void turnLeft() {
        this.entityShape.setRotate(this.entityShape.getRotate() - 5);
        logger.info("Turned left. New rotation: " + this.getEntityShape().getRotate());
    }

    public void turnRight() {
        this.entityShape.setRotate(this.entityShape.getRotate() + 5);
        logger.info("Turned right. New rotation: " + this.getEntityShape().getRotate());
    }
}