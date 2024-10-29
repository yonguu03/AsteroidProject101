package se233.asteroidproject.controllers;

public class GameState {
    private int score;
    private int playerHealth;
    private double playerX;
    private double playerY;

    public GameState() {
        this.score = 0;
        this.playerHealth = 100;
        this.playerX = 0;
        this.playerY = 0;
    }

    public synchronized int getScore() {
        return score;
    }

    public synchronized void setScore(int score) {
        this.score = score;
    }

    public synchronized int getPlayerHealth() {
        return playerHealth;
    }

    public synchronized void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public synchronized double getPlayerX() {
        return playerX;
    }

    public synchronized void setPlayerX(double playerX) {
        this.playerX = playerX;
    }

    public synchronized double getPlayerY() {
        return playerY;
    }

    public synchronized void setPlayerY(double playerY) {
        this.playerY = playerY;
    }
}