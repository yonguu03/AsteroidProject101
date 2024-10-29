package se233.asteroidproject.characters;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AsteroidsVar {

    public static class LargeAsteroids extends se233.asteroidproject.characters.Asteroids {
        public LargeAsteroids(double x, double y, double velocity) {
            super(new ImageView(new Image(LargeAsteroids.class.getResource("/LargeAsteroid.png").toExternalForm())), x, y, velocity);
        }

        public LargeAsteroids() {
            super(new ImageView(new Image(LargeAsteroids.class.getResource("/LargeAsteroid.png").toExternalForm())), 900, 600, 0.5);
        }
    }

    public static class MediumAsteroids extends se233.asteroidproject.characters.Asteroids {
        public MediumAsteroids(double x, double y, double velocity) {
            super(new ImageView(new Image(MediumAsteroids.class.getResource("/MediumAsteroid.png").toExternalForm())), x, y, velocity);
        }

        public MediumAsteroids() {
            super(new ImageView(new Image(MediumAsteroids.class.getResource("/MediumAsteroid.png").toExternalForm())), 900, 600, 1.0);
        }
    }

    public static class SmallAsteroids extends se233.asteroidproject.characters.Asteroids {
        public SmallAsteroids(double x, double y, double velocity) {
            super(new ImageView(new Image(SmallAsteroids.class.getResource("/SmallAsteroid.png").toExternalForm())), x, y, velocity);
        }

        public SmallAsteroids() {
            super(new ImageView(new Image(SmallAsteroids.class.getResource("/SmallAsteroid.png").toExternalForm())), 900, 600, 1.2);
        }
    }
}