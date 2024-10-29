package se233.asteroidproject.characters;

import se233.asteroidproject.characters.builds.Characters;
import se233.asteroidproject.main.AsteroidsGame;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;

public abstract class Asteroids extends Characters {
    protected ImageView entityShape;
    protected double velocity;
    protected double angle;
    private Point2D location;

    public Asteroids(ImageView entityShape, double x, double y, double velocity) {
        super(entityShape, x, y); // Call to the Characters constructor
        this.entityShape = entityShape;
        this.velocity = velocity;
        this.location = new Point2D(x, y);
    }

    public ImageView getEntityShape() {
        return entityShape;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void move() {
        double radians = Math.toRadians(angle);
        double changeX = Math.cos(radians) * velocity;
        double changeY = Math.sin(radians) * velocity;
        location = location.add(changeX, changeY);
        entityShape.setTranslateX(location.getX());
        entityShape.setTranslateY(location.getY());

        // keep the characters in the screen
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

    //asteroid collisions
    public void handleCollision(List<Bullet> bullets, List<Asteroids> asteroids, PlayerShip playerShip, Pane pane) {
        // Remove bullets and asteroids that have collided
        bullets.removeIf(bullet -> {
            for (Asteroids asteroid : asteroids) {
                if (bullet.hasCollided(asteroid)) {
                    pane.getChildren().remove(bullet.getEntityShape());
                    // divide the large and medium asteroids when hit
                    if (asteroid instanceof AsteroidsVar.LargeAsteroids) {
                        AsteroidsVar.MediumAsteroids newAsteroid1 = new AsteroidsVar.MediumAsteroids(asteroid.getEntityShape().getTranslateX(), asteroid.getEntityShape().getTranslateY(), velocity);
                        AsteroidsVar.MediumAsteroids newAsteroid2 = new AsteroidsVar.MediumAsteroids(asteroid.getEntityShape().getTranslateX(), asteroid.getEntityShape().getTranslateY(), velocity);
                        asteroids.add(newAsteroid1);
                        asteroids.add(newAsteroid2);
                        newAsteroid1.setAngle(Math.random() * 360);
                        newAsteroid2.setAngle(Math.random() * 360);
                        pane.getChildren().add(newAsteroid1.getEntityShape());
                        pane.getChildren().add(newAsteroid2.getEntityShape());
                    } else if (asteroid instanceof AsteroidsVar.MediumAsteroids) {
                        AsteroidsVar.SmallAsteroids newAsteroid1 = new AsteroidsVar.SmallAsteroids(asteroid.getEntityShape().getTranslateX(), asteroid.getEntityShape().getTranslateY(), velocity);
                        AsteroidsVar.SmallAsteroids newAsteroid2 = new AsteroidsVar.SmallAsteroids(asteroid.getEntityShape().getTranslateX(), asteroid.getEntityShape().getTranslateY(), velocity);
                        asteroids.add(newAsteroid1);
                        asteroids.add(newAsteroid2);
                        newAsteroid1.setAngle(Math.random() * 360);
                        newAsteroid2.setAngle(Math.random() * 360);
                        pane.getChildren().add(newAsteroid1.getEntityShape());
                        pane.getChildren().add(newAsteroid2.getEntityShape());
                    }
                    pane.getChildren().remove(asteroid.getEntityShape());
                    asteroids.remove(asteroid);
                    return true;
                }
            }
            return false;
        });

        // Check collisions for playerShip
        for (Asteroids asteroid : asteroids) {
            if (asteroid.getEntityShape().getBoundsInParent().intersects(playerShip.getEntityShape().getBoundsInParent())) {
                playerShip.decreaseLife();
            }
        }
    }
}