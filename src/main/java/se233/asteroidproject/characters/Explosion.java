package se233.asteroidproject.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class Explosion {
    private double x, y;
    private Image spriteSheet;
    private int frame = 0;
    private int maxFrames = 16; // Number of frames the explosion will last
    private int columns = 16; // Number of columns in the sprite sheet
    private int rows = 1; // Number of rows in the sprite sheet
    private int totalFrames = columns * rows;
    private double frameWidth;
    private double frameHeight;
    private int delayCounter = 0; // Counter to control the frame update speed
    private int delayThreshold = 3; // Adjust this value to slow down the animation
    private double scale = 0.5; // Scale factor for the animation size

    public Explosion(double x, double y) {
        this.x = x;
        this.y = y;
        this.spriteSheet = new Image(Objects.requireNonNull(getClass().getResourceAsStream("explosion.png")));
        this.frameWidth = spriteSheet.getWidth() / columns;
        this.frameHeight = spriteSheet.getHeight() / rows;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void update() {
        delayCounter++;
        if (delayCounter >= delayThreshold) {
            frame++;
            delayCounter = 0;
        }
    }

    public boolean isFinished() {
        return frame >= maxFrames;
    }

    public void draw(GraphicsContext gc) {
        int currentFrame = frame % totalFrames;
        int frameX = (currentFrame % columns) * (int) frameWidth;
        int frameY = (currentFrame / columns) * (int) frameHeight;

        double scaledWidth = frameWidth * scale;
        double scaledHeight = frameHeight * scale;

        gc.drawImage(spriteSheet, frameX, frameY, frameWidth, frameHeight, x - scaledWidth / 2, y - scaledHeight / 2, scaledWidth, scaledHeight);
    }
}