package se233.asteroidproject.characters.builds;

import se233.asteroidproject.interfaces.Turnable;
import javafx.scene.image.ImageView;

public abstract class TurnableCharacters extends Characters implements Turnable {
    public TurnableCharacters(ImageView polygon, int x, int y) {
        super(polygon, x, y);
    }

    public abstract void move();

    public void setVelocity(){
        super.setVelocity(velocityX, velocityX);
    }

    public void turnLeft() {
        this.getEntityShape().setRotate(this.getEntityShape().getRotate() - 5);
    }

    public void turnRight() {
        this.getEntityShape().setRotate(this.getEntityShape().getRotate() + 5);
    }
}
