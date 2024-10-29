package se233.asteroidproject.characters;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se233.asteroidproject.characters.builds.Characters;
import java.util.logging.Logger;

public class Boss extends Characters {
    private static final Logger logger = Logger.getLogger(Boss.class.getName());
    private int velocity;
    private double angle;

    public Boss() {
        super(new ImageView(), 0, (int) (600 * Math.random()));
        try {
            Image alienImage = new Image(Boss.class.getResource("/Alien.png").toExternalForm());
            this.entityShape.setImage(alienImage);
        } catch (Exception e) {
            logger.severe("Failed to load alien image: " + e.getMessage());
        }
        this.velocity = 1;
        this.angle = Math.random() * 2 * Math.PI;
        this.entityShape.setScaleX(3.0);
        this.entityShape.setScaleY(3.0);
    }

    @Override
    public void move() {
        this.entityShape.setTranslateX(this.entityShape.getTranslateX() + this.velocity * Math.cos(angle));
        this.entityShape.setTranslateY(this.entityShape.getTranslateY() + this.velocity * Math.sin(angle));
        super.setEntityWithinScene();
    }
}