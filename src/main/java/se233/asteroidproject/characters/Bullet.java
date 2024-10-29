package se233.asteroidproject.characters;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se233.asteroidproject.characters.builds.Characters;


public class Bullet extends Characters {
    private double origin_x;
    private double origin_y;
    private long createdTime = 0;

    public double getOrigin_x() {
        return origin_x;
    }

    public void setOrigin_x(double origin_x) {
        this.origin_x = origin_x;
    }

    public double getOrigin_y() {
        return origin_y;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setOrigin_y(double origin_y) {
        this.origin_y = origin_y;
    }

    public Bullet(int origin_x, int origin_y, double shipWidth, double shipHeight) {
        // make sure the bullet is being shot from the ship's location
        super(new ImageView(new Image(Bullet.class.getResource("/Bullet.png").toExternalForm())),
                origin_x + shipWidth / 2, origin_y + shipHeight / 2);
        setOrigin_x(origin_x + shipWidth / 2);
        setOrigin_y(origin_y + shipHeight / 2);
        setCreatedTime(System.currentTimeMillis());
    }

    public void move() {
        double changeX = Math.cos(Math.toRadians(this.getEntityShape().getRotate()));
        double changeY = Math.sin(Math.toRadians(this.getEntityShape().getRotate()));

        this.getEntityShape().setTranslateX(this.getEntityShape().getTranslateX() + changeX * 5);
        this.getEntityShape().setTranslateY(this.getEntityShape().getTranslateY() + changeY * 5);
    }
}